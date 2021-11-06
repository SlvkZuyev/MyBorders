package com.overtimedevs.bordersproject.data.repository

import android.annotation.SuppressLint
import com.overtimedevs.bordersproject.data.data_source.local.CountryLocalDataSource
import com.overtimedevs.bordersproject.domain.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.data_source.remote.CountryRemoteDataSource
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class CountryRepository (
    private val netManager: NetManager,
    private val sessionRepository: SessionRepository,
    private val localDataSource: CountryLocalDataSource,
    private val remoteDataSource: CountryRemoteDataSource
) {

    @SuppressLint("CheckResult")
    fun getAllCountries(
        originCountryCode: String = sessionRepository.getSessionInfo().loadedCountriesOriginCode,
        loadFromRemote: Boolean = false
    ): Observable<List<Country>> {
        if (netManager.isConnected() && loadFromRemote) {
            return remoteDataSource.getCountries(originCountryCode).doOnNext {
                localDataSource.saveCountries(it)
                refreshSessionInfo(originCountryCode)
            }
        }
        return localDataSource.getAllCountries()
    }

    fun getSavedCountries(): Observable<List<Country>> {
        return localDataSource.getAllCountries()
    }

    fun loadCountries(): Observable<List<Country>> {
        val loadedOrigin = sessionRepository.getSessionInfo().loadedCountriesOriginCode
        if (loadedOrigin == SessionInfo.defaultLoadedCountriesOrigin) {
            return Observable.just(emptyList())
        }
        return if (netManager.isConnected()) {
            remoteDataSource.getCountries()
        } else {
            Observable.just(emptyList())
        }
    }

    //todo: Починить это ебаное говнище
    fun getTrackedCountries(): Observable<List<Country>> {
        return localDataSource.getTrackedCountries()
    }

    fun addTrackedCountryById(countryId: Int) {
        localDataSource.trackCountryById(countryId)
    }

    fun removeTrackedCountryById(countryId: Int) {
        localDataSource.removeTrackedCountryById(countryId)
    }

    fun getTrackedCountriesStatistic(): Observable<CountriesStatistic> {
        return localDataSource.getTrackedCountriesStatistic()
    }

    fun getAllCountriesStatistic(): Observable<CountriesStatistic> {
        return localDataSource.getAllCountriesStatistic()
    }

    fun getCountryById(countryId: Int): Observable<Country?> {
        return localDataSource.getCountryById(countryId)
    }

    private fun refreshSessionInfo(loadedCountry: String) {
        sessionRepository.saveSessionInfo(
            SessionInfo(
                loadedCountriesOriginCode = loadedCountry,
                lastFetchTime = System.currentTimeMillis()
            )
        )
    }
}
