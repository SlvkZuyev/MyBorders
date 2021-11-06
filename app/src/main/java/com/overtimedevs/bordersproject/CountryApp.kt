package com.overtimedevs.bordersproject

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CountryApp : Application() {

//    companion object {
//        lateinit var retrofit: Retrofit
//        lateinit var countryDatabase: CountryDatabase
//    }

//    override fun onCreate() {
//        super.onCreate()
//        countryDatabase = Room.databaseBuilder(
//            applicationContext,
//            CountryDatabase::class.java,
//            CountryDatabase.DATABASE_NAME
//        )
//            .fallbackToDestructiveMigration()
//            .build()
//
//        retrofit = Retrofit.Builder()
//            .baseUrl("https://www.kayak.com/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .build()
//    }

}
