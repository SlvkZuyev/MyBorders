package com.overtimedevs.bordersproject.data.repository

import android.annotation.SuppressLint
import android.util.Log
import com.overtimedevs.bordersproject.data.data_source.local.CountryDao
import com.overtimedevs.bordersproject.data.data_source.local.CountryLocalDataSource
import com.overtimedevs.bordersproject.data.data_source.local.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.data_source.remote.CountryRemoteDataSource
import com.overtimedevs.bordersproject.data.data_source.remote.CountryApi
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import io.reactivex.Single


class CountryRepository(
    private val netManager: NetManager,
    countryDao: CountryDao,
    countryApi: CountryApi
) {
    private val localDataSource = CountryLocalDataSource(countryDao)
    private val remoteDataSource = CountryRemoteDataSource(countryApi)

    @SuppressLint("CheckResult")
    fun getAllCountries(): Observable<List<Country>> {
       if(netManager.isConnected()){
           return remoteDataSource.getCountries().doOnNext {
               localDataSource.saveCountries(it)
           }
       }
        return localDataSource.getAllCountries()
    }

    //todo: Починить это ебаное говнище
    fun getTrackedCountries(): Observable<List<Country>> {
        return localDataSource.getTrackedCountries()
    }

    fun addTrackedCountryById(countryId: Int){
        localDataSource.trackCountryById(countryId)
    }

    fun removeTrackedCountryById(countryId: Int){
        localDataSource.removeTrackedCountryById(countryId)
    }

    fun getTreckedRestr(): Observable<Int>{
        return localDataSource.getTreckedRestr()
    }

    fun getTrackedCountriesStatistic() : Observable<CountriesStatistic>{
        return localDataSource.getTrackedCountriesStatistic()
    }

    fun getAllCountriesStatistic() : Observable<CountriesStatistic>{
        return localDataSource.getAllCountriesStatistic()
    }
}
