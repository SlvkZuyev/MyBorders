package com.overtimedevs.bordersproject.presentation.main_activity.fragments.all_countries_fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel
import com.overtimedevs.bordersproject.presentation.utils.ListsConcatenator
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AllCountriesViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _countriesCards = MutableLiveData<List<CountryCardItemViewModel>>(emptyList())
    val countriesCards: LiveData<List<CountryCardItemViewModel>> = _countriesCards

    var showedCountryItemViewModels: List<CountryCardItemViewModel> = emptyList()
    var canDisplayChanges = false

    var newDataCounter = 0

    //Todo: Probably here we should use Observable.zip method
    fun loadAllCountries() {
        compositeDisposable += Observable
            .concat(countryRepository.getAllCountries(), countryRepository.getTrackedCountries())
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
                        val newData = t.map { it.toCountryCard() }
                        newDataCounter++
                        onNewDataReceived(newData)
                    }
                })
    }

    fun onNewDataReceived(newData: List<CountryCardItemViewModel>) {
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

        showedCountryItemViewModels = ListsConcatenator.withoutDuplicates(
            showedCountryItemViewModels,
            newData
        )

        showChangesIfPossible()
    }

    private fun showChangesIfPossible(){
        if (canDisplayChanges) {
            _countriesCards.value = showedCountryItemViewModels
        } else
        if (newDataCounter < 3) {
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
}