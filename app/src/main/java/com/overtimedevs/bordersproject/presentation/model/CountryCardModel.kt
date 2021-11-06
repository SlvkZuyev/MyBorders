package com.overtimedevs.bordersproject.presentation.model

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class CountryCardModel(
    val countryId: Int,
    val borderStatus: String,
    val countryName: String,
    val message: String,
    val trackStatus: Boolean
) : ViewModel() {
    var isTracked = ObservableBoolean(trackStatus)

    var onTrackStatusChanged : ((CountryCardModel) -> Unit)? = null
    var onCountryClicked : ((CountryCardModel) -> Unit)? = null

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