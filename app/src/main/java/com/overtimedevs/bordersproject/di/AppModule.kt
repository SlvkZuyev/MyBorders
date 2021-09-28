package com.overtimedevs.bordersproject.di

import android.app.Application
import androidx.room.Room
import com.overtimedevs.bordersproject.data.data_source.CountryDatabase
import com.overtimedevs.bordersproject.data.repository.CountryRepositoryImpl
import com.overtimedevs.bordersproject.domain.repositiory.CountryRepository
import com.overtimedevs.bordersproject.domain.use_case.CountryUseCases
import com.overtimedevs.bordersproject.domain.use_case.GetCountries
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //TODO: read about Hilt
    //TODO: read about Dagger
    //TODO: read about Singleton

    @Provides
    @Singleton
    fun provideCountriesDatabase(app: Application): CountryDatabase {
       return Room.databaseBuilder(
            app,
            CountryDatabase::class.java,
            CountryDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideCountryRepository(db: CountryDatabase) : CountryRepository{
        return CountryRepositoryImpl(db.countryDao)
    }

    @Provides
    @Singleton
    fun provideUseCases(repository: CountryRepository): CountryUseCases {
        return CountryUseCases(
            getCountries = GetCountries(repository)
        )
    }
}