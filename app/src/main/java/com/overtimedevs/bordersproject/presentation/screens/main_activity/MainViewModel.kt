package com.overtimedevs.bordersproject.presentation.screens.main_activity

import android.util.Log
import androidx.lifecycle.*
import com.overtimedevs.bordersproject.domain.model.CountriesStatistic
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.data.repository.SessionRepository
import com.overtimedevs.bordersproject.domain.model.SessionInfo
import com.overtimedevs.bordersproject.domain.model.UserSettings
import com.overtimedevs.bordersproject.domain.use_case.GetOnNewSettingsAppliedMessage
import com.overtimedevs.bordersproject.domain.use_case.session_info.SessionUseCases
import com.overtimedevs.bordersproject.domain.use_case.statistic.StatisticUseCases
import com.overtimedevs.bordersproject.extensions.plusAssign
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val statistic: StatisticUseCases,
    private val sessionUseCases: SessionUseCases,
) : ViewModel() {

    private val _showedStatistic: MutableLiveData<CountriesStatistic> = MutableLiveData(
        CountriesStatistic(1, 2, 3)
    )
    val showedStatistic: LiveData<CountriesStatistic> = _showedStatistic
    private val TAG = "MainViewModel"
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
        compositeDisposable += statistic.getTrackedCountriesStatistic()
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
        compositeDisposable += statistic.getAllCountriesStatistic()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(
                object : DisposableObserver<CountriesStatistic>() {

                    override fun onError(e: Throwable) {
                        Log.d(TAG, "onError: ${e.message}")
                    }

                    override fun onComplete() {
                        Log.d(TAG, "onComplete: ")
                    }

                    override fun onNext(t: CountriesStatistic) {
                        Log.d(
                            TAG,
                            "onNext: new Statistic: restr: ${t.restrictions} open: ${t.open} cls: ${t.closed}"
                        )
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

    fun isFirstOpen(): Boolean {
        val sessionInfo = sessionUseCases.getSessionInfo()
        return sessionInfo.lastFetchTime == SessionInfo.defaultLastFetchTime
    }

    fun getOnNewSettingsAppliedMessage(
        newSettings: UserSettings,
        oldSettings: UserSettings
    ): String {
        return GetOnNewSettingsAppliedMessage(
            oldSettings = oldSettings,
            newSettings = newSettings
        )
    }

}