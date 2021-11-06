package com.overtimedevs.bordersproject.data.data_source.local

import android.annotation.SuppressLint
import androidx.room.Database
import androidx.room.RoomDatabase
import com.overtimedevs.bordersproject.data.model.TrackedCountry
import com.overtimedevs.bordersproject.domain.model.Country


@SuppressLint("RestrictedApi")
@Database(
    entities = [(Country::class), (TrackedCountry::class)],
    version = 4,
    exportSchema = true,

)

abstract class CountryDatabase: RoomDatabase() {
    abstract val countryDao: CountryDao

    companion object{
        const val DATABASE_NAME = "countries_db"
    }
}