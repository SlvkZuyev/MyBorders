package com.overtimedevs.bordersproject.domain.model

class UserSettings(
    var originCountry: String,
    var isVaccinated: Boolean
) {
    companion object{
        const val defaultOriginCountry = "undefined"
        const val defaultVaccinationStatus = false
    }
}