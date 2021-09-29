package com.overtimedevs.bordersproject.data.repository

import com.overtimedevs.bordersproject.data.data_source.CountryLocalDataSource
import com.overtimedevs.bordersproject.data.data_source.CountryRemoteDataSource
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable

class CountriesRepository(private val netManager: NetManager) {
    private val localDataSource = CountryLocalDataSource()
    private val remoteDataSource = CountryRemoteDataSource()

    fun getRepositories(): Observable<List<Country>> {
        netManager.isConnectedToInternet?.let {
            if (it) {
                //todo save those data to local data store
                return remoteDataSource.getCountries()
            }
        }

        return localDataSource.getCountries()
    }
}