package com.overtimedevs.bordersproject.data.data_source.local

import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable

class CountryLocalDataSource(private val countryDao: CountryDao) {

    fun getCountries(): Observable<List<Country>> {
        return countryDao.getAll()
    }

    fun saveCountries(countries: List<Country>){
        for(country in countries){
            countryDao.insert(country)
        }
    }

}