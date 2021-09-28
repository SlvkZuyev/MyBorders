package com.overtimedevs.bordersproject.data.country
import com.google.gson.annotations.SerializedName

data class ReturnQuarantineStatus (

	@SerializedName("status") val status : Boolean,
	@SerializedName("messageId") val messageId : String,
	@SerializedName("exceptions") val exceptions : String,
	@SerializedName("body") val body : String
)