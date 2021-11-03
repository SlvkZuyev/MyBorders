package com.overtimedevs.bordersproject.di

import android.content.Context
import androidx.room.Room
import com.overtimedevs.bordersproject.data.data_source.local.CountryDao
import com.overtimedevs.bordersproject.data.data_source.local.CountryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideCountryDatabase(@ApplicationContext appContext: Context): CountryDatabase =
        Room.databaseBuilder(
            appContext,
            CountryDatabase::class.java,
            CountryDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideCountryDao(countryDatabase: CountryDatabase): CountryDao =
        countryDatabase.countryDao
}