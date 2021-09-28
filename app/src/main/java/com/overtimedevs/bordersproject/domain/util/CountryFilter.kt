package com.overtimedevs.bordersproject.domain.util

//TODO: read about Sealed classes
sealed class CountryFilter {
    object All: CountryFilter()
    object LikedOnly: CountryFilter()
}