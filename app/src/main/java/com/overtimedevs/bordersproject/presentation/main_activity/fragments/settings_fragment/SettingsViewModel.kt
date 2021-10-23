package com.overtimedevs.bordersproject.presentation.main_activity.fragments.settings_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.UserSettings
import java.util.*
import kotlin.collections.ArrayList

class SettingsViewModel(val repository: UserRepository) : ViewModel() {
    // TODO: Implement the ViewModel
    val savedUserSettings = repository.getUserSettings()
    val newUserSettings = savedUserSettings

    fun getCountries() : List<String>{
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()

        Log.d("SlvkLog", "${countries.toString()}")
        return countries
    }

    fun onClickSubmit(){
        repository.saveUserSettings(newUserSettings)
    }

    fun setOriginCountry(countryName: String){
        newUserSettings.originCountry = countryName
    }

    fun setVaccinationsStatus(isVaccinated: Boolean){
        newUserSettings.isVaccinated = isVaccinated
    }

    private fun getCountryCodeByName(countryName: String) : String? {
        return Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
    }

}