package com.overtimedevs.bordersproject.data.data_source.remote.model

import com.google.gson.annotations.SerializedName

data class MaskStatus (

	@SerializedName("status") val status : String,
	@SerializedName("messageId") val messageId : String,
	@SerializedName("exceptions") val exceptions : String,
	@SerializedName("body") val body : String
)