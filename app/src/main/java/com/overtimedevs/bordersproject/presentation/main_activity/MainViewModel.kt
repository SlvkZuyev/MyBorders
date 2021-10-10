package com.overtimedevs.bordersproject.presentation.main_activity

import android.util.Log
import androidx.lifecycle.*
import com.overtimedevs.bordersproject.data.data_source.local.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import com.overtimedevs.bordersproject.extensions.toCountryCard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val countryRepository: CountryRepository) : ViewModel() {
    private val _showedStatistic : MutableLiveData<CountriesStatistic> = MutableLiveData(
        CountriesStatistic(1, 2, 3)
    )
    val showedStatistic : LiveData<CountriesStatistic> = _showedStatistic

    var allCountriesStatistic : CountriesStatistic? = null
    var trackedCountriesStatistic : CountriesStatistic? = null
    private val compositeDisposable = CompositeDisposable()
    var onCountryClick : ((Int) ->Unit)? = null

    private var currentPageNum = 0
    init {
        loadTrackedCountriesStatistic()
        loadAllCountriesStatistic()
    }

    private fun loadTrackedCountriesStatistic() {
        compositeDisposable += countryRepository.getTrackedCountriesStatistic()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<CountriesStatistic>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: CountriesStatistic) {
                        trackedCountriesStatistic = t
                        if(currentPageNum == 0){
                            _showedStatistic.value = trackedCountriesStatistic
                        }
                    }
                })
    }

    fun loadAllCountriesStatistic() {
        compositeDisposable += countryRepository.getAllCountriesStatistic()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<CountriesStatistic>() {

                    override fun onError(e: Throwable) {
                        Log.d("ViewModel", "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d("ViewModel", "onComplete: ")
                    }

                    override fun onNext(t: CountriesStatistic) {
                        allCountriesStatistic = t
                    }
                })
    }


    fun onPageChanged(newPageNumber : Int){
        currentPageNum = newPageNumber
        if(currentPageNum == 0){
            showTrackedCountriesStatistic()
        } else if(currentPageNum == 1){
            showAllCountriesStatistic()
        }
    }

    private fun showAllCountriesStatistic(){
        _showedStatistic.value = allCountriesStatistic
    }

    private fun showTrackedCountriesStatistic(){
        _showedStatistic.value = trackedCountriesStatistic
    }
}