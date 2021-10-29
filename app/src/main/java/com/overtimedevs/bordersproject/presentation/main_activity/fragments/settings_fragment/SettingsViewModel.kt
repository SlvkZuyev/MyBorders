package com.overtimedevs.bordersproject.presentation.main_activity.fragments.settings_fragment

import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.UserSettings
import java.util.*
import kotlin.collections.ArrayList

class SettingsViewModel(val repository: UserRepository) : ViewModel() {
    val savedUserSettings = repository.getUserSettings()
    val newUserSettings = UserSettings()
    init{
        newUserSettings.originCountry = savedUserSettings.originCountry
        newUserSettings.isVaccinated = savedUserSettings.isVaccinated
    }

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

        return countries
    }

    fun saveNewSettings(){
        repository.saveUserSettings(newUserSettings)
    }

    fun setOriginCountry(countryName: String){
        if(countryName != ""){
            newUserSettings.originCountry = countryName
        }
    }

    fun setVaccinationsStatus(isVaccinated: Boolean){
        newUserSettings.isVaccinated = isVaccinated
    }

    private fun getCountryCodeByName(countryName: String) : String? {
        return Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
    }

    fun settingsChanged(): Boolean{
        return  savedUserSettings.isVaccinated != newUserSettings.isVaccinated ||
                savedUserSettings.originCountry != newUserSettings.originCountry
    }

    fun settingsAreValid() : Boolean{
        return newUserSettings.originCountry != UserSettings.defaultOriginCountry
    }
}