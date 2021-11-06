package com.overtimedevs.bordersproject.presentation.screens.tracked_countries

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.use_case.country.CountryUseCases
import com.overtimedevs.bordersproject.domain.use_case.session_info.SessionUseCases
import com.overtimedevs.bordersproject.domain.use_case.user_settings.UserSettingsUseCases
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.model.CountryCardModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class TrackedCountriesViewModel @Inject constructor(
    private val countryUseCases: CountryUseCases
) : ViewModel() {
    private val TAG = "TrackedCountriesViewModel"
    private val compositeDisposable = CompositeDisposable()

    private val _countriesCards = MutableLiveData<List<CountryCardModel>>(emptyList())
    val countriesCards: LiveData<List<CountryCardModel>> = _countriesCards

    var canShowChanges = false
    var isFirstTimeDisplayed = false

    var onCountriesLoaded: (List<CountryCardModel>) -> Unit = {}

    fun loadTrackedCountries() {
        compositeDisposable += countryUseCases.getTrackedCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d(TAG, "onNext: ${t.size}")
                        onNewDataReceived(t.map { it.toCountryCard() })
                    }
                })
    }


    fun onNewDataReceived(newData: List<CountryCardModel>){
        newData.forEach {
            it.apply {
                onCountryClicked = { country -> onCountryClicked(country) }
                onTrackStatusChanged =
                    { country -> onCountryTrackStatusChange(country) }
                setIsTracked(true)
            }
        }

        _countriesCards.value = newData

        if (!isFirstTimeDisplayed) {
            _countriesCards.value = newData
            isFirstTimeDisplayed = true
        }

        onCountriesLoaded(newData)
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun onCountryTrackStatusChange(countryCardModel: CountryCardModel) {
        if (countryCardModel.isTracked.get()) {
            countryUseCases.addTrackedCountryById(countryCardModel.countryId)
        } else {
            countryUseCases.removeTrackedCountryById(countryCardModel.countryId)
        }
    }

    private fun onCountryClicked(countryCardModel: CountryCardModel) {
        Log.d("SlvkLog", "CountryClicked")
    }
}
