package com.overtimedevs.bordersproject.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import com.overtimedevs.bordersproject.domain.model.UserSettings

class SessionRepository(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences("session_info",
        Context.MODE_PRIVATE
    )

    fun getSessionInfo() : SessionInfo {
        val originCountry = preferences.getString(LOADED_COUNTRIES_ORIGIN, SessionInfo.defaultLoadedCountriesOrigin) ?:
        UserSettings.defaultOriginCountry
        val fetchTime = preferences.getLong(LAST_FETCH_TIME, SessionInfo.defaultLastFetchTime)

        return SessionInfo(originCountry, fetchTime)
    }

    fun saveSessionInfo(sessionInfo: SessionInfo){
        with(preferences.edit()){
            putString(LOADED_COUNTRIES_ORIGIN, sessionInfo.loadedCountriesOriginCode)
            putLong(LAST_FETCH_TIME, sessionInfo.lastFetchTime)
        }.apply()
    }

    companion object {
        private const val LOADED_COUNTRIES_ORIGIN = "loaded_countries_origin"
        private const val LAST_FETCH_TIME = "last_fetch_time"
    }
}