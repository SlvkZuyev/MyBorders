package com.overtimedevs.bordersproject.domain.use_case.statistic

import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.CountriesStatistic
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

class GetAllCountriesStatistic @Inject constructor(
    private val countryRepository: CountryRepository
    ) {

    operator fun invoke() : Observable<CountriesStatistic> {
        return countryRepository.getAllCountriesStatistic()
    }
}