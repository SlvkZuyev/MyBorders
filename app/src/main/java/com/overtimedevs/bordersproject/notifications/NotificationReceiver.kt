package com.overtimedevs.bordersproject.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.data.data_source.local.CountryDatabase
import com.overtimedevs.bordersproject.data.data_source.remote.CountryApi
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import android.graphics.BitmapFactory
import com.overtimedevs.bordersproject.presentation.main_activity.MainActivity
import retrofit2.converter.gson.GsonConverterFactory


class NotificationReceiver : BroadcastReceiver() {
    var savedCountries = emptyList<Country>()
    var loadedCountries = emptyList<Country>()
    var changedCountries = emptyList<Country>()

    lateinit var countryRepository: CountryRepository
    private val compositeDisposable = CompositeDisposable()
    lateinit var context: Context
    private val notificationId = 1212

    override fun onReceive(context: Context?, p1: Intent?) {
        this.context = context!!

        Log.d("APPLICATION", "received!!!!")
        Toast.makeText(context, "received!!!!", Toast.LENGTH_LONG).show()
        initRepository(context!!)

        loadTrackedCountries()
    }

    fun initRepository(context: Context){
        val countryDatabase = Room.databaseBuilder(
            context,
            CountryDatabase::class.java,
            CountryDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.kayak.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        val countryDao = countryDatabase.countryDao
        val countyApi = retrofit.create(CountryApi::class.java)
        val sessionRepository = SessionRepository(context)

        countryRepository = CountryRepository(
            countryApi = countyApi,
            countryDao = countryDao,
            netManager = NetManager(context),
            sessionRepository = sessionRepository
        )
    }

    fun loadTrackedCountries(){
        compositeDisposable += countryRepository.getTrackedCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        savedCountries = t
                        loadAllCountries()
                    }
                })
    }

    fun loadAllCountries(){
        compositeDisposable += countryRepository.getAllCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        loadedCountries = t
                        changedCountries = getRefreshedCountries()
                        showNotification()
                    }
                })
    }

    fun getRefreshedCountries() : List<Country>{
        val changedCountries: MutableList<Country> = mutableListOf()
        for(country in savedCountries){
            if(!loadedCountries.contains(country)){
                changedCountries.add(country)
            }
        }
        return changedCountries
    }

    private fun showNotification(){

        if(changedCountries.isEmpty()){
            return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pandingIntent = PendingIntent.getActivity(context, 0,
            intent, 0)


        val largeIcon = BitmapFactory.decodeResource(context.resources, R.drawable.icon_push)
        var builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.starr_checked)
            .setLargeIcon(largeIcon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pandingIntent)
            .setAutoCancel(true)

        createNotificationChannel()

        if(changedCountries.size == 1){
            builder
                .setContentTitle("You have some changes!")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Restrictions in ${changedCountries[0].countryName} has been changed"))
        }

        if(changedCountries.size > 1){
            builder
                .setContentTitle("You have some changes!")
                .setStyle(NotificationCompat.BigTextStyle()
                    .bigText("Restrictions in ${changedCountries.size} countries has been changed"))
        }

        with(NotificationManagerCompat.from(context)) {
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification_channel"
            val descriptionText = "channel_descr"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


}