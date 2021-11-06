package com.overtimedevs.bordersproject.domain.use_case.country

import com.overtimedevs.bordersproject.domain.model.Country

class CopyTrackStatuses {
    operator fun invoke(to: List<Country>, from: List<Country>) : List<Country>{

        val listWithCopiedTrackedCountries = mutableListOf<Country>()
        listWithCopiedTrackedCountries.addAll(to)

        for(destinationCountry in listWithCopiedTrackedCountries){
            destinationCountry.isTracked = from.contains(destinationCountry)
        }

        return listWithCopiedTrackedCountries
    }
}