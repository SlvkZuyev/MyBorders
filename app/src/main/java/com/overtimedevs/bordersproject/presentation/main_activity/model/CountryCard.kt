package com.overtimedevs.bordersproject.presentation.main_activity.model

class CountryCard(
    val countryId: Int,
    val borderStatus: String,
    val countryName: String,
    val message: String,
    var isTracked: Boolean = false
) {
    var onTrackStatusChanged : ((CountryCard) -> Unit)? = null
    var onCountryClicked : ((CountryCard) -> Unit)? = null
}