package com.overtimedevs.bordersproject.presentation.main_activity

import android.content.pm.PackageInstaller
import android.util.Log
import androidx.lifecycle.*
import com.overtimedevs.bordersproject.data.data_source.local.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import com.overtimedevs.bordersproject.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val countryRepository: CountryRepository,
    private val sessionRepository: SessionRepository
) : ViewModel() {
    private val _showedStatistic: MutableLiveData<CountriesStatistic> = MutableLiveData(
        CountriesStatistic(1, 2, 3)
    )
    val showedStatistic: LiveData<CountriesStatistic> = _showedStatistic

    private val _message: MutableLiveData<String> = MutableLiveData("")
    val message: LiveData<String> = _message

    var allCountriesStatistic: CountriesStatistic? = null
    var trackedCountriesStatistic: CountriesStatistic? = null


    private val compositeDisposable = CompositeDisposable()

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
                        if (currentPageNum == 0) {
                            _showedStatistic.value = trackedCountriesStatistic
                        }
                        showMessageIfNeeded()
                    }
                })
    }

    private fun loadAllCountriesStatistic() {
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
                        if (currentPageNum == 1) {
                            _showedStatistic.value = allCountriesStatistic
                        }
                        showMessageIfNeeded()
                    }
                })
    }


    fun onPageChanged(newPageNumber: Int) {
        currentPageNum = newPageNumber
        if (currentPageNum == 0) {
            showTrackedCountriesStatistic()
        } else if (currentPageNum == 1) {
            showAllCountriesStatistic()
        }
        showMessageIfNeeded()
    }

    private fun showAllCountriesStatistic() {
        _showedStatistic.value = allCountriesStatistic
    }

    private fun showTrackedCountriesStatistic() {
        _showedStatistic.value = trackedCountriesStatistic
    }

    fun notifySettingsChanged() {
        refreshStatistic()
    }

    private fun refreshStatistic() {
        onPageChanged(currentPageNum)
    }

    fun showMessageIfNeeded() {
        if (currentPageNum == 0) {
            if (trackedCountriesStatistic?.isEmpty() == true) {
                _message.value = "Page 0"
            }
        } else if (currentPageNum == 1) {
            if (allCountriesStatistic?.isEmpty() == true) {
                _message.value = "Page 1"
            }
        }

    }

    fun isFirstOpen() : Boolean{
        val sessionInfo = sessionRepository.getSessionInfo()
        return sessionInfo.lastFetchTime == SessionInfo.defaultLastFetchTime
    }


}