package com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.overtimedevs.bordersproject.CountryApp
import com.overtimedevs.bordersproject.data.data_source.remote.CountryApi
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.data.util.NetManager

class AllCountriesViewModelProviderFactory (val app: CountryApp) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        val countryDao = CountryApp.countryDatabase.countryDao
        val countyApi = CountryApp.retrofit.create(CountryApi::class.java)
        val countryRepository = com.overtimedevs.bordersproject.data.repository.CountryRepository(
            countryApi = countyApi,
            countryDao = countryDao,
            netManager = NetManager(app.applicationContext)
        )
        val userRepository = UserRepository(context = app.applicationContext)

        val viewModel = AllCountriesViewModel(countryRepository, userRepository)
        return viewModel as T
    }

}