package com.overtimedevs.bordersproject.domain.use_case

import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.repositiory.CountryRepository
import com.overtimedevs.bordersproject.domain.util.CountryFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCountries(private val repository: CountryRepository) {
//
//    operator fun invoke(filter: CountryFilter = CountryFilter.All): Flow<List<com.overtimedevs.bordersproject.data.data_source.remote.retrofit.model.country.Country>> {
//        return repository.getCountries().map { countries ->
//            when(filter){
//                is CountryFilter.All -> {
//                    countries
//                }
//                is CountryFilter.LikedOnly -> {
//                    countries.filter { country -> country.isTracked }
//                }
//            }
//        }
//
//    }
}