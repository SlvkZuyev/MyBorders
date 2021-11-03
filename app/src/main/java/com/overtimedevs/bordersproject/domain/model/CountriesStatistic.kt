package com.overtimedevs.bordersproject.domain.model

class CountriesStatistic(
    val restrictions: Int,
    val open: Int,
    val closed: Int
) {
    fun isEmpty(): Boolean{
        return restrictions == 0 &&
                open == 0 &&
                closed == 0
    }
}