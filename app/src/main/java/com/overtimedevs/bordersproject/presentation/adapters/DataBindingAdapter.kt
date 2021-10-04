package com.overtimedevs.bordersproject.presentation.adapters

import androidx.databinding.BindingAdapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.main_activity.adapters.CountriesRecyclerViewAdapter


@BindingAdapter("app:srcCompat")
fun setImageResource(imageView: ImageView, resource: Int?) {
    if (resource != null) {
        with(imageView) { setImageResource(resource) }
    }
}

@BindingAdapter("app:items")
fun setItems(recyclerView: RecyclerView, items: List<Country>){
    recyclerView.adapter = CountriesRecyclerViewAdapter(items)
}