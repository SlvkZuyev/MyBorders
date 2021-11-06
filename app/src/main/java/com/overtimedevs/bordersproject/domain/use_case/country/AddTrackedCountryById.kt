package com.overtimedevs.bordersproject.domain.use_case.country

import com.overtimedevs.bordersproject.data.repository.CountryRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddTrackedCountryById @Inject constructor(private val countryRepository: CountryRepository) {
    operator fun invoke(countryId: Int){
        countryRepository.addTrackedCountryById(countryId)
    }
}