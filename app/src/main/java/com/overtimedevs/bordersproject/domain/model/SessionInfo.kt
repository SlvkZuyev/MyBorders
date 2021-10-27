package com.overtimedevs.bordersproject.domain.model

class SessionInfo(
    var loadedCountriesOriginCode: String,
    var lastFetchTime: Long,
) {
    companion object{
        const val defaultLoadedCountriesOrigin = "undefined"
        const val defaultLastFetchTime = 0L
    }
}