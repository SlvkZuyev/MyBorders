package com.overtimedevs.bordersproject.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.overtimedevs.bordersproject.domain.model.Country

@Database(
    entities = [Country::class],
    version = 1
)

abstract class CountryDatabase: RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object{
        const val DATABASE_NAME = "countries_db"
    }
}