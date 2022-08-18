package com.overtimedevs.bordersproject.data.data_source.local

import com.overtimedevs.bordersproject.domain.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.model.TrackedCountry
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class CountryLocalDataSource @Inject constructor(private val countryDao: CountryDao) {

    fun getAllCountries(): Observable<List<Country>> {
        return countryDao.getAllCountries()
    }

    fun getCountryById(countryId: Int) : Observable<Country?> {
        return countryDao.getCountryById(countryId)
    }

    fun getTrackedCountries(): Observable<List<Country>> {
        return countryDao.getTrackedCountries()
    }

    fun saveCountries(countries: List<Country>) {
        countryDao.insert(countries)
    }

    fun trackCountryById(countryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            countryDao.addTrackedCountry(TrackedCountry(countryId))
        }
    }

    fun removeTrackedCountryById(countryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            countryDao.removeTrackedCountry(countryId)
        }
    }

    fun getAllCountriesStatistic(): Observable<CountriesStatistic> {
        return Observable.zip(
            countryDao.getAllCountriesRestrictionCount(),
            countryDao.getAllCountriesOpenCount(),
            countryDao.getAllCountriesClosedCount()
        ) { restriction, open, closed ->
            CountriesStatistic(
                restrictions = restriction,
                open = open,
                closed = closed
            )
        }
    }

    fun getTrackedCountriesStatistic(): Observable<CountriesStatistic> {
        return Observable.zip(
            countryDao.getTrackedCountriesRestrictionCount(),
            countryDao.getTrackedCountriesOpenCount(),
            countryDao.getTrackedCountriesClosedCount()
        ) { restriction, open, closed ->
            CountriesStatistic(
                restrictions = restriction,
                open = open,
                closed = closed
            )
        }
    }
}