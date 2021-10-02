package com.overtimedevs.bordersproject.data.data_source.remote

import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.toModel
import io.reactivex.Observable

class CountryRemoteDataSource(private val countryApi: CountryApi? = null) {

    fun getCountries(): Observable<List<Country>> {
        return countryApi?.getCountries()
            ?.map { countryDto ->
                List(countryDto.size)
                { index ->
                    countryDto[index].toModel()
                }
            }!!
    }
}
