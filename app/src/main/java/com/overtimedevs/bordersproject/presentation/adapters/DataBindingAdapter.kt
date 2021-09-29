package com.overtimedevs.bordersproject.presentation.adapters

import androidx.databinding.BindingAdapter

import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView


@BindingAdapter("app:srcCompat")
fun setImageResource(imageView: ImageView, resource: Int?) {
    if (resource != null) {
        with(imageView) { setImageResource(resource) }
    }
}