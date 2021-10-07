package com.overtimedevs.bordersproject.presentation.main_activity.fragments.tracked_countries_fragment

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCard
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class TrackedCountriesViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val isLoading = ObservableField(false)

    private val _countriesCards = MutableLiveData<List<CountryCard>>(emptyList())
    val countriesCards: LiveData<List<CountryCard>> = _countriesCards

    var showedCountries: List<CountryCard> = emptyList()

    fun loadTrackedCountries() {
        compositeDisposable += countryRepository.getTrackedCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("Tracked", "onError: ${e.message}")
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                    }

                    override fun onComplete() {
                        Log.d("Tracked", "onComplete: ")
                        isLoading.set(false)
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d("Tracked", "onNext: ")
                        showedCountries = t.map { it.toCountryCard() }
                        showedCountries.forEach {
                            it.apply {
                                isTracked = true
                                onCountryClicked = { country -> onCountryClicked(country) }
                                onTrackStatusChanged =
                                    { country -> onCountryTrackStatusChange(country) }
                            }
                        }
                        _countriesCards.value = showedCountries
                        //_countriesCards.value = trackedCountries
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun onCountryTrackStatusChange(countryCard: CountryCard) {
        Log.d("SlvkLog", "State Changed")
        if (countryCard.isTracked) {
            countryRepository.addTrackedCountryById(countryCard.countryId)
        } else {
            Log.d("SlvkLog", "Removed")
            countryRepository.removeTrackedCountryById(countryCard.countryId)
        }
    }

    fun onCountryClicked(countryCard: CountryCard) {
        Log.d("SlvkLog", "CountryClicked")
    }
}
