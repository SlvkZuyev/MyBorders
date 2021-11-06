package com.overtimedevs.bordersproject.domain.use_case

import com.overtimedevs.bordersproject.domain.model.UserSettings

object GetOnNewSettingsAppliedMessage {

    private var newSettings: UserSettings? = null
    private var oldSettings: UserSettings? = null

    operator fun invoke(
        oldSettings: UserSettings,
        newSettings: UserSettings
    ): String {
        this.newSettings = newSettings
        this.oldSettings = oldSettings

        if (changedMoreThenOneValue()) {
            return "New settings applied"
        }

        if (originCountryChanged()) {
            return "Origin country changed to: ${newSettings.originCountry}"
        }

        if(vaccinationStatusChanged()){
            if(newSettings.isVaccinated){
                return "You are vaccinated now"
            } else {
                return "You are not vaccinated"
            }
        }

        if(notificationStatusChanged()){
            if(newSettings.isNotificationsEnabled){
                return "Notifications enabled"
            } else {
                return "Notifications disabled"
            }
        }

        return ""
    }


    private fun changedMoreThenOneValue(): Boolean {
        val values =
            listOf(
                originCountryChanged(),
                vaccinationStatusChanged(),
                notificationStatusChanged()
            )

        var numberOfChangedValues = 0

        values.forEach {isChanged ->
            if (isChanged) {
                numberOfChangedValues++
            }
        }

        return numberOfChangedValues >= 2
    }

    private fun originCountryChanged(): Boolean {
        return newSettings?.originCountry != oldSettings?.originCountry
    }

    private fun vaccinationStatusChanged(): Boolean {
        return newSettings?.isVaccinated != oldSettings?.isVaccinated
    }

    private fun notificationStatusChanged(): Boolean {
        return newSettings?.isNotificationsEnabled != oldSettings?.isNotificationsEnabled
    }

}