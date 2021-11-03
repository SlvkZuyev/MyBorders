package com.overtimedevs.bordersproject.data.data_source.remote

import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.toModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Observable
import javax.inject.Inject

class CountryRemoteDataSource @Inject constructor (private val countryApi: CountryApi) {

    fun getCountries(originCountryCode: String = "ua"): Observable<List<Country>> {
        return countryApi.getCountries(originCountryCode)
            .map { countryDto ->
                List(countryDto.size)
                { index ->
                    countryDto[index].toModel()
                }
            }!!
    }
}
