package com.overtimedevs.bordersproject.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.overtimedevs.bordersproject.domain.model.UserSettings
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository (context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("user_settings", MODE_PRIVATE)

    fun getUserSettings() : UserSettings{
        val originCountry = preferences.getString(ORIGIN_COUNTRY, UserSettings.defaultOriginCountry) ?:
            UserSettings.defaultOriginCountry
        val isVaccinated = preferences.getBoolean(IS_VACCINATED, UserSettings.defaultVaccinationStatus)
        val isNotificationsEnabled = preferences.getBoolean(NOTIFICATION_STATUS, UserSettings.defaultNotificationStatus)

        return UserSettings(
            originCountry = originCountry,
            isVaccinated = isVaccinated,
            isNotificationsEnabled = isNotificationsEnabled)
    }

    fun saveUserSettings(userSettings: UserSettings){
        with(preferences.edit()){
            putString(ORIGIN_COUNTRY, userSettings.originCountry)
            putBoolean(IS_VACCINATED, userSettings.isVaccinated)
            putBoolean(NOTIFICATION_STATUS, userSettings.isNotificationsEnabled)
        }.apply()
    }

    companion object {
        private const val ORIGIN_COUNTRY = "origin_country"
        private const val IS_VACCINATED = "is_vaccinated"
        private const val NOTIFICATION_STATUS = "is_notifications_enabled"
    }
}