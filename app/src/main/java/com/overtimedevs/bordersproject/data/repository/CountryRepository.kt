package com.overtimedevs.bordersproject.data.repository

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

    fun getCountries(): Observable<List<Country>> {
        netManager.isConnectedToInternet?.let {
            return remoteDataSource.getCountries()
        }
        return localDataSource.getCountries()
    }
}
