package com.overtimedevs.bordersproject.presentation.screens.all_countries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import com.overtimedevs.bordersproject.domain.model.UserSettings
import com.overtimedevs.bordersproject.domain.use_case.country.CountryUseCases
import com.overtimedevs.bordersproject.domain.use_case.session_info.SessionUseCases
import com.overtimedevs.bordersproject.domain.use_case.user_settings.UserSettingsUseCases
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.model.CountryCardModel
import com.overtimedevs.bordersproject.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AllCountriesViewModel @Inject constructor (
    private val countryUseCases: CountryUseCases,
    private val userSettingsUseCases: UserSettingsUseCases,
    private val sessionUseCases: SessionUseCases
) : ViewModel() {
    private val TAG = "AllCountriesViewModel"

    private val compositeDisposable = CompositeDisposable()
    var userSettings = userSettingsUseCases.getUserSettings()

    private val _countriesCards = MutableLiveData<List<CountryCardModel>>(emptyList())
    val countriesCards: LiveData<List<CountryCardModel>> = _countriesCards

    var showedCountryModels: List<CountryCardModel> = emptyList()
    var canDisplayChanges = false

    var onCountriesLoaded: (List<CountryCardModel>) -> Unit = {}
    var onCountriesStartLoading: () -> Unit = {}

    fun settingsNotApplied() : Boolean{
        return userSettings.originCountry == UserSettings.defaultOriginCountry
    }

    private fun markCountries(allCountries: List<Country>, trackedCountries: List<Country>) : List<Country>{
        for(trackedCountry in trackedCountries){
            allCountries.find { it.countryId == trackedCountry.countryId }?.isTracked = true
        }
        return allCountries
    }

    private fun changeMarkedCountries(allCountries: List<CountryCardModel>, trackedCountries: List<Country>) : List<CountryCardModel>{
        allCountries.forEach { it.setIsTracked(false) }
        for(trackedCountry in trackedCountries){
            allCountries.find { it.countryId == trackedCountry.countryId }?.setIsTracked(true)
        }
        return allCountries
    }

    init {
        //loadTrackedCountries()
    }

    fun loadAllCountries(forceShowChanges: Boolean = false){
        if(userSettings.originCountry == UserSettings.defaultOriginCountry){
            Log.d(
                TAG,
                "loadAllCountries: Countries not loaded because(origin country: ${userSettings.originCountry})"
            )
            return
        }
        onCountriesStartLoading()

        compositeDisposable += countryUseCases
            .getAllCountries(
                countryCode = getCountryCode(userSettings.originCountry),
                forceLoadFromRemote = shouldLoadFromRemote())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("AllCountriesVM", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("AllCountriesVM", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d("AllCountriesVM", "onNext: got new to ${userSettings.originCountry}")
                        val newData = t.map { it.toCountryCard() }
                        onNewDataReceived(newData, forceShowChanges)
                    }
                })
    }

    fun onTrackedCountriesReceived(trackedCountries: List<Country>){
        if(canDisplayChanges){
            val allCountries = countriesCards.value
            _countriesCards.value = changeMarkedCountries(allCountries!!, trackedCountries)
        }

    }

    fun onNewDataReceived(newData: List<CountryCardModel>, forceShowChanges: Boolean) {
        newData.forEach {
            it.apply {
                onCountryClicked = { country ->
                    onCountryClicked(country)
                }
                onTrackStatusChanged = { country ->
                    onCountryTrackStatusChange(country)
                }
            }
        }

        showedCountryModels = newData

        if(forceShowChanges){
            Log.d("SlvkLog", "changes are shown")
            _countriesCards.value = showedCountryModels
        } else {
            showChangesIfPossible()
        }

        onCountriesLoaded(newData)
    }

    private fun showChangesIfPossible() {
        if (canDisplayChanges) {
            _countriesCards.value = showedCountryModels
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun onCountryTrackStatusChange(countryCardModel: CountryCardModel) {
        if (countryCardModel.isTracked.get()) {
            countryUseCases.addTrackedCountryById (countryCardModel.countryId)
        } else {
            countryUseCases.removeTrackedCountryById(countryCardModel.countryId)
        }
    }

    private fun onCountryClicked(countryCardModel: CountryCardModel) {
        Log.d("SlvkLog", "CountryClicked")
    }

    fun notifySettingsChanged() {
        userSettings = userSettingsUseCases.getUserSettings()
        loadAllCountries(forceShowChanges = true)
    }

    private fun getCountryCode(countryName: String): String {
        val code = Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
        return code ?: ""
    }

    private fun shouldLoadFromRemote() : Boolean{
        val sessionInfo = sessionUseCases.getSessionInfo()

        //If data has not been loaded yet
        if(sessionInfo.loadedCountriesOriginCode == SessionInfo.defaultLoadedCountriesOrigin){
            Log.d(TAG, "New info loaded from remote. Reason: Loaded first time")
            return true
        }

        //if origin for countries in db is not correct
        if(sessionInfo.loadedCountriesOriginCode != getCountryCode(userSettings.originCountry)){
            Log.d(TAG,
                "New info loaded from remote. "+
                    "Reason: origin country changed (" +
                    "old: ${sessionInfo.loadedCountriesOriginCode}, " +
                    "new: ${getCountryCode(userSettings.originCountry)})")
            return true
        }

        //if data in db is outdated
        if(sessionInfo.lastFetchTime - System.currentTimeMillis() > Constants.MINIMUM_DATA_FETCH_INTERVAL){
            Log.d(TAG, "New info loaded from remote. Reason: data was outdated")
            return true
        }

        Log.d(TAG, "New info loaded from local")
        return false
    }
}