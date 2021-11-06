package com.overtimedevs.bordersproject.domain.use_case.statistic

import javax.inject.Inject

data class StatisticUseCases @Inject constructor(
    val getTrackedCountriesStatistic: GetTrackedCountriesStatistic,
    val getAllCountriesStatistic: GetAllCountriesStatistic
)