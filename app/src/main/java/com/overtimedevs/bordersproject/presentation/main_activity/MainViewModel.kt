package com.overtimedevs.bordersproject.presentation.main_activity

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.extensions.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val countryRepository: CountryRepository) : ViewModel() {

}