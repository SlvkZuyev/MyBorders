package com.overtimedevs.bordersproject.presentation.main_activity.fragments.tracked_countries_fragment

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.data.repository.UserRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TrackedCountriesViewModel(
    private val countryRepository: CountryRepository,
    private val userRepository: UserRepository,
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val isLoading = ObservableField(false)

    private val _countriesCards = MutableLiveData<List<CountryCardItemViewModel>>(emptyList())
    val countriesCards: LiveData<List<CountryCardItemViewModel>> = _countriesCards

    var showedCountryItemViewModels: List<CountryCardItemViewModel> = emptyList()
    var canShowChanges = false
    var isFirstTimeDisplayed = false

    var onCountriesLoaded: (List<Country>) -> Unit = {}

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
                        showedCountryItemViewModels = t.map { it.toCountryCard() }
                        showedCountryItemViewModels.forEach {
                            it.apply {
                                onCountryClicked = { country -> onCountryClicked(country) }
                                onTrackStatusChanged =
                                    { country -> onCountryTrackStatusChange(country) }
                                setIsTracked(true)
                            }
                        }

                        if (canShowChanges) {
                            _countriesCards.value = showedCountryItemViewModels
                        }

                        if (!isFirstTimeDisplayed) {
                            _countriesCards.value = showedCountryItemViewModels
                            isFirstTimeDisplayed = true
                        }

                        onCountriesLoaded(t)
                    }
                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    fun onCountryTrackStatusChange(countryCardItemViewModel: CountryCardItemViewModel) {
        Log.d("SlvkLog", "State Changed")
        if (countryCardItemViewModel.isTracked.get()) {
            countryRepository.addTrackedCountryById(countryCardItemViewModel.countryId)
        } else {
            Log.d("SlvkLog", "Removed")
            countryRepository.removeTrackedCountryById(countryCardItemViewModel.countryId)
        }
    }

    fun onCountryClicked(countryCardItemViewModel: CountryCardItemViewModel) {
        Log.d("SlvkLog", "CountryClicked")
    }
}
