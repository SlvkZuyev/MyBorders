package com.overtimedevs.bordersproject.presentation.adapters

import android.util.Log
import androidx.databinding.BindingAdapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesRVAdapter
import com.overtimedevs.bordersproject.presentation.main_activity.model.CountryCardItemViewModel


@BindingAdapter("app:srcCompat")
fun setImageResource(imageView: ImageView, resource: Int?) {
    if (resource != null) {
        with(imageView) { setImageResource(resource) }
    }
}

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<CountryCardItemViewModel>){
 if(recyclerView.adapter is CountriesRVAdapter){
        (recyclerView.adapter as CountriesRVAdapter).setNewList(items)
    }
}

@BindingAdapter("app:isTracked")
fun setTrackStatusIcon(imageView: ImageView, isTracked: Boolean){
    Log.d("SlvkLog:BindingAdapter", "iconChanged")
    if(isTracked){
        imageView.setImageResource(R.drawable.starr_checked)
    } else {
        imageView.setImageResource(R.drawable.starr_unchecked)
    }
}

@BindingAdapter("app:borderStatus")
fun setBorderStatus(imageView: ImageView, status: String){
    var resId =
    when(status){
        "RESTRICTIONS" -> R.drawable.country_status_restriction
        "OPEN" -> R.drawable.country_status_open
        "CLOSED" -> R.drawable.country_status_closed
        else -> R.drawable.country_status_closed
    }

    imageView.setImageResource(resId)
}