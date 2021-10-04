package com.overtimedevs.bordersproject.data.data_source.local

import androidx.annotation.WorkerThread
import com.overtimedevs.bordersproject.data.data_source.local.model.TrackedCountry
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CountryLocalDataSource(private val countryDao: CountryDao) {

    fun getAllCountries(): Observable<List<Country>> {
        return countryDao.getAllCountries()
    }

    fun getTrackedCountries(): Observable<List<Country>> {
        return countryDao.getTrackedCountries()
    }

    fun saveCountries(countries: List<Country>){
        for(country in countries){
            countryDao.insert(country)
        }
    }

    fun trackCountryById(countryId: Int){
        GlobalScope.launch {
            countryDao.addTrackedCountry(TrackedCountry(countryId))
        }
    }

}