package com.overtimedevs.bordersproject.data.country
import com.google.gson.annotations.SerializedName

data class ArrivalQuarantineStatus (
	@SerializedName("status") val status : Boolean,
	@SerializedName("messageId") val messageId : String,
	@SerializedName("exceptions") val exceptions : String,
	@SerializedName("body") val body : String
)