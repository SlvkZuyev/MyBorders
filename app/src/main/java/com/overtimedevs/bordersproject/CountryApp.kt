package com.overtimedevs.bordersproject

import android.app.Application
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.overtimedevs.bordersproject.data.data_source.local.CountryDatabase
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//
@HiltAndroidApp
class CountryApp : Application() {
    companion object {
        lateinit var retrofit: Retrofit
        lateinit var countryDatabase: CountryDatabase
    }

    override fun onCreate() {
        super.onCreate()
        countryDatabase = Room.databaseBuilder(
            applicationContext,
            CountryDatabase::class.java,
            CountryDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.kayak.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}
