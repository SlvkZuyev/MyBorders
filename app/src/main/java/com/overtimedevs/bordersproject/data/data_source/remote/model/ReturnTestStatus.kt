package com.overtimedevs.bordersproject.data.data_source.remote.model

import com.google.gson.annotations.SerializedName


data class ReturnTestStatus (

	@SerializedName("status") val status : String,
	@SerializedName("messageId") val messageId : Int,
	@SerializedName("exceptions") val exceptions : String,
	@SerializedName("body") val body : String
)