package com.overtimedevs.bordersproject.data.model.country_response

import com.google.gson.annotations.SerializedName


data class VariantData (
	@SerializedName("destinationType") val destinationType : String,
	@SerializedName("variants") val variants : Variants
)