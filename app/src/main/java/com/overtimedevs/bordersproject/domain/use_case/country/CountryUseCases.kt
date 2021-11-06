package com.overtimedevs.bordersproject.domain.use_case.country

import com.overtimedevs.bordersproject.domain.use_case.user_settings.GetCountryById
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class CountryUseCases @Inject constructor(
    val getTrackedCountries: GetTrackedCountries,
    val getAllCountries: GetAllCountries,
    val getCountryById: GetCountryById,
    val removeTrackedCountryById: RemoveTrackedCountryById,
    val addTrackedCountryById: AddTrackedCountryById,
)
