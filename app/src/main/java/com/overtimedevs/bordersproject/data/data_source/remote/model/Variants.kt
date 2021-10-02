package com.overtimedevs.bordersproject.data.data_source.remote.model

import com.google.gson.annotations.SerializedName


data class Variants (
	@SerializedName("default") val default : String,
	@SerializedName("from") val from : String,
	@SerializedName("to") val to : String,
	@SerializedName("at") val at : String,
	@SerializedName("in") val _in : String,
	@SerializedName("near") val near : String,
	@SerializedName("around") val around : String,
	@SerializedName("on") val on : String
)