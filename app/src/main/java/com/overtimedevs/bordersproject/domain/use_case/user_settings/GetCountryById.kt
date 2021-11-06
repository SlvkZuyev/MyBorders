package com.overtimedevs.bordersproject.domain.use_case.user_settings

import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import javax.inject.Inject

class GetCountryById @Inject constructor(
    private val countryRepository: CountryRepository
) {
    operator fun invoke(countryId: Int): Observable<Country?> {
        return countryRepository.getCountryById(countryId)
    }

}