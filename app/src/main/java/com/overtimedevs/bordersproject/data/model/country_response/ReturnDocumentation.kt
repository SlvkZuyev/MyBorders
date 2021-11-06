package com.overtimedevs.bordersproject.data.model.country_response

import com.google.gson.annotations.SerializedName


data class ReturnDocumentation (

	@SerializedName("name") val name : String,
	@SerializedName("url") val url : String,
	@SerializedName("explanation") val explanation : String
)