package com.overtimedevs.bordersproject.presentation.main_activity

import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.use_case.GetCountries
import com.overtimedevs.bordersproject.domain.util.CountryFilter

data class MainState(
    //TODO: add states
    val countries: List<Country> = emptyList(),
    val countryFilter: CountryFilter = CountryFilter.All,
    val isSearchPanelVisible: Boolean = false
)
