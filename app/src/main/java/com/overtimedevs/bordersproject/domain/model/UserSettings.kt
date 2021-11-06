package com.overtimedevs.bordersproject.domain.model

class UserSettings(
    var originCountry: String = defaultOriginCountry,
    var isVaccinated: Boolean = defaultVaccinationStatus,
    var isNotificationsEnabled: Boolean = defaultNotificationStatus
) {
    companion object{
        const val defaultOriginCountry = "undefined"
        const val defaultVaccinationStatus = false
        const val defaultNotificationStatus = false
    }
}