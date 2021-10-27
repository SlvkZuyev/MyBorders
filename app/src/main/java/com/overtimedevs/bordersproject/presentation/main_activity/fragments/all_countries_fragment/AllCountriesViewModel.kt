package com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import com.overtimedevs.bordersproject.domain.model.UserSettings
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel
import com.overtimedevs.bordersproject.presentation.utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class AllCountriesViewModel(
    private val countryRepository: CountryRepository,
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val TAG = "AllCountriesViewModel"

    private val compositeDisposable = CompositeDisposable()
    var userSettings = userRepository.getUserSettings()

    private val _countriesCards = MutableLiveData<List<CountryCardItemViewModel>>(emptyList())
    val countriesCards: LiveData<List<CountryCardItemViewModel>> = _countriesCards

    var showedCountryItemViewModels: List<CountryCardItemViewModel> = emptyList()
    var canDisplayChanges = false

    var onCountriesLoaded: (List<Country>) -> Unit = {}

    private fun markCountries(allCountries: List<Country>, trackedCountries: List<Country>) : List<Country>{
        for(trackedCountry in trackedCountries){
            allCountries.find { it.countryId == trackedCountry.countryId }?.isTracked = true
        }
        return allCountries
    }

    private fun changeMarkedCountries(allCountries: List<CountryCardItemViewModel>, trackedCountries: List<Country>) : List<CountryCardItemViewModel>{
        allCountries.forEach { it.setIsTracked(false) }
        for(trackedCountry in trackedCountries){
            allCountries.find { it.countryId == trackedCountry.countryId }?.setIsTracked(true)
        }
        return allCountries
    }

    init {
        loadTrackedCountries()
    }

    fun loadAllCountries(forceShowChanges: Boolean = false){
        if(userSettings.originCountry == UserSettings.defaultOriginCountry){
            Log.d(
                TAG,
                "loadAllCountries: Countries not loaded because(origin country: ${userSettings.originCountry})"
            )
            return
        }
        compositeDisposable += Observable
            .zip(countryRepository.getAllCountries(
                getCountryCode(userSettings.originCountry)!!,
                shouldLoadFromRemote()
            ), countryRepository.getTrackedCountries(),
                {allCountries, trackedCountries -> markCountries(allCountries, trackedCountries)}
            )
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d("SlvkLog", "got new To ${userSettings.originCountry}")
                        val newData = t.map { it.toCountryCard() }
                        onNewDataReceived(newData, forceShowChanges)
                        onCountriesLoaded(t)
                    }
                })
    }

    private fun loadTrackedCountries(){
        compositeDisposable += countryRepository.getTrackedCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        onTrackedCountriesReceived(t)
                    }
                })
    }

    fun onTrackedCountriesReceived(trackedCountries: List<Country>){
        if(canDisplayChanges){
            val allCountries = countriesCards.value
            _countriesCards.value = changeMarkedCountries(allCountries!!, trackedCountries)
        }

    }

    fun onNewDataReceived(newData: List<CountryCardItemViewModel>, forceShowChanges: Boolean) {
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

        showedCountryItemViewModels = newData

        if(forceShowChanges){
            Log.d("SlvkLog", "changes are shown")
            _countriesCards.value = showedCountryItemViewModels
        } else {
            showChangesIfPossible()
        }
    }

    private fun showChangesIfPossible() {
        if (canDisplayChanges) {
            _countriesCards.value = showedCountryItemViewModels
        }
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun onCountryTrackStatusChange(countryCardItemViewModel: CountryCardItemViewModel) {
        Log.d("SlvkLog", "State Changed")
        if (countryCardItemViewModel.isTracked.get()) {
            countryRepository.addTrackedCountryById(countryCardItemViewModel.countryId)
        } else {
            Log.d("SlvkLog", "Removed")
            countryRepository.removeTrackedCountryById(countryCardItemViewModel.countryId)
        }
    }

    private fun onCountryClicked(countryCardItemViewModel: CountryCardItemViewModel) {
        Log.d("SlvkLog", "CountryClicked")
    }

    fun notifySettingsChanged() {
        userSettings = userRepository.getUserSettings()
        loadAllCountries(forceShowChanges = true)
    }

    private fun getCountryCode(countryName: String): String {
        val code = Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }
        return code ?: ""
    }

    private fun shouldLoadFromRemote() : Boolean{
        val sessionInfo = sessionRepository.getSessionInfo()

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