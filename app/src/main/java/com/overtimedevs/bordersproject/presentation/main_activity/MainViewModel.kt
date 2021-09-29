package com.overtimedevs.bordersproject.presentation.main_activity

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.overtimedevs.bordersproject.data.repository.CountriesRepository
import com.overtimedevs.bordersproject.data.util.NetManager
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel (application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    var countryRepository: CountriesRepository = CountriesRepository(NetManager(getApplication()))
    val isLoading = ObservableField(false)

    private val _countries = MutableLiveData<List<Country>>(emptyList())
    val countries : LiveData<List<Country>> = _countries

    init{
        loadRepositories()
    }

    private fun loadRepositories() {
        isLoading.set(true)

        compositeDisposable += countryRepository
            .getRepositories()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<Country>>() {

            override fun onError(e: Throwable) {
                //if some error happens in our data layer our app will not crash, we will
                // get error here
            }

            override fun onComplete() {
                isLoading.set(false)
            }

            override fun onNext(t: List<Country>) {
                _countries.value = t
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