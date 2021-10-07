package com.overtimedevs.bordersproject.data.data_source.local

import androidx.room.*
import com.overtimedevs.bordersproject.data.data_source.local.model.TrackedCountry

import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun getAllCountries(): Observable<List<Country>>

    @Query("SELECT * FROM country WHERE countryId = :id")
    fun getById(id: Int): Observable<Country?>

    @Query("SELECT * FROM country INNER JOIN trackedcountry ON country.countryId = trackedcountry.countryId")
    fun getTrackedCountries(): Observable<List<Country>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: List<Country>)

    @Update
    fun update(country: Country) : Completable

    @Delete
    fun delete(country: Country) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTrackedCountry(trackedCountry: TrackedCountry)

    /*
    @Delete
    fun removeTrackedCountry(trackedCountry: TrackedCountry)

     */

    @Query("DELETE FROM trackedcountry WHERE countryId = :id")
    fun removeTrackedCountry(id: Int)
}