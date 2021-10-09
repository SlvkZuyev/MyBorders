package com.overtimedevs.bordersproject.presentation.main_activity.model

import android.util.Log
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CountryCardItemViewModel(
    val countryId: Int,
    val borderStatus: String,
    val countryName: String,
    val message: String,
) : ViewModel() {
    var isTracked = ObservableBoolean(false)

    var onTrackStatusChanged : ((CountryCardItemViewModel) -> Unit)? = null
    var onCountryClicked : ((CountryCardItemViewModel) -> Unit)? = null

    fun onStarClick(){
        Log.d("SlvkLog:CountryCardItemViewModel", "isTracked: ${isTracked.get()}")
        isTracked.set(!isTracked.get())
        onTrackStatusChanged?.invoke(this)
    }

    fun setIsTracked(value: Boolean){
        isTracked.set(value)
    }

    fun onCountryClicked(){
        onCountryClicked?.invoke(this)
    }
}