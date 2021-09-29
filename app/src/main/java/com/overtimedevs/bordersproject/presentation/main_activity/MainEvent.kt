package com.overtimedevs.bordersproject.presentation.main_activity

import com.overtimedevs.bordersproject.domain.util.CountryFilter

sealed class MainEvent{
    data class Filter(val countryFilter: CountryFilter)  : MainEvent()
    data class TrackCountry(val countryFilter: CountryFilter)  : MainEvent()
}
