package com.overtimedevs.bordersproject.data.country
import com.google.gson.annotations.SerializedName

data class MaskStatus (
	@SerializedName("status") val status : String,
	@SerializedName("messageId") val messageId : String,
	@SerializedName("exceptions") val exceptions : String,
	@SerializedName("body") val body : String
)