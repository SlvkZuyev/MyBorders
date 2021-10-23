package com.overtimedevs.bordersproject.data.data_source.remote

import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.toModel
import io.reactivex.Observable

class CountryRemoteDataSource(private val countryApi: CountryApi? = null) {

    fun getCountries(originCountryCode: String = "ua"): Observable<List<Country>> {
        return countryApi?.getCountries(originCountryCode)
            ?.map { countryDto ->
                List(countryDto.size)
                { index ->
                    countryDto[index].toModel()
                }
            }!!
    }
}
