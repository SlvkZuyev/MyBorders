package com.overtimedevs.bordersproject.presentation.main_activity.countries_list_fragment.all_countries_fragment

import android.util.Log
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

class AllCountriesViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _countriesCards = MutableLiveData<List<CountryCard>>(emptyList())
    val countriesCards: LiveData<List<CountryCard>> = _countriesCards

    var trackedCountries : List<CountryCard> = emptyList()

    init {
        countryRepository.addTrackedCountryById(2)
        countryRepository.addTrackedCountryById(5)
        countryRepository.addTrackedCountryById(10)
        countryRepository.addTrackedCountryById(20)
        //countryRepository.getAllCountries()
    }

    fun loadCountries(trackedOnly: Boolean) {
        compositeDisposable += countryRepository.getAllCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("SlvkLog", "onError: ${e.message}")
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                    }

                    override fun onComplete() {
                        Log.d("SlvkLog", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d("SlvkLog", "onNext: ")
                        trackedCountries = t.map { it.toCountryCard() }
                        _countriesCards.value = trackedCountries
                    }
                })
    }

    private fun loadAllCountries() {
        compositeDisposable += countryRepository.getAllCountries()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<List<Country>>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                        //if some error happens in our data layer our app will not crash, we will
                        // get error here
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: List<Country>) {
                        Log.d("ViewModel", "onNext: ")
                        _countriesCards.value = t.map { it.toCountryCard() }
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}