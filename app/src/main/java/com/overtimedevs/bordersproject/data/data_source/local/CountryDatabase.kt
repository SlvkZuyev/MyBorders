package com.overtimedevs.bordersproject.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.overtimedevs.bordersproject.data.data_source.local.CountryDao
import com.overtimedevs.bordersproject.data.data_source.local.model.TrackedCountry
import com.overtimedevs.bordersproject.domain.model.Country

@Database(
    entities = [(Country::class), (TrackedCountry::class)],
    version = 3
)

abstract class CountryDatabase: RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object{
        const val DATABASE_NAME = "countries_db"
    }
}