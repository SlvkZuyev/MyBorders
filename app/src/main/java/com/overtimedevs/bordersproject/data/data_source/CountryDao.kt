package com.overtimedevs.bordersproject.data.data_source

import androidx.room.*

import com.overtimedevs.bordersproject.data.country.NetworkCountryDescription
import com.overtimedevs.bordersproject.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryDao {
    @Query("SELECT * FROM country")
    fun getAll(): Flow<List<Country>>

    @Query("SELECT * FROM country WHERE countryId = :id")
    fun getById(id: Int): Country?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Update
    fun update(country: Country)

    @Delete
    fun delete(country: Country)
}