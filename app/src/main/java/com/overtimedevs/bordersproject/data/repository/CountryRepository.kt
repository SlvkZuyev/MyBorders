package com.overtimedevs.bordersproject.data.repository

import android.annotation.SuppressLint
import com.overtimedevs.bordersproject.data.data_source.local.CountryDao
import com.overtimedevs.bordersproject.data.data_source.local.CountryLocalDataSource
import com.overtimedevs.bordersproject.data.data_source.remote.CountryRemoteDataSource
import com.overtimedevs.bordersproject.data.data_source.remote.CountryApi
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable


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

    fun getTrackedCountries(): Observable<List<Country>> {
        return localDataSource.getTrackedCountries()
    }

    fun addTrackedCountryById(countryId: Int){
        localDataSource.trackCountryById(countryId)
    }
}
