package com.overtimedevs.bordersproject.data.data_source.local

import androidx.room.*

import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun getAll(): Observable<List<Country>>

    @Query("SELECT * FROM country WHERE countryId = :id")
    fun getById(id: Int): Observable<Country?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(country: Country)

    @Update
    fun update(country: Country) : Completable

    @Delete
    fun delete(country: Country) : Completable
}