package com.overtimedevs.bordersproject.domain.use_case.country

import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTrackedCountries @Inject constructor(private val countryRepository: CountryRepository) {
    operator fun invoke() : Observable<List<Country>> {
        return countryRepository.getTrackedCountries()
    }
}