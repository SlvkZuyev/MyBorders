package com.overtimedevs.bordersproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.overtimedevs.bordersproject.R
import com.overtimedevs.bordersproject.data.country.*

@Entity
class Country(

    @PrimaryKey
    val countryId: Int,
    val isTracked: Boolean = false,
    val lastModified: Int,
    val borderStatus: String,
    val countryName: String,

    //Arrival
    val arrivalTestRequired: Boolean,
    val arrivalQuarantineRequired: Boolean,
    val arrivalQuarantineMessage: String,

    //Return
    val returnTestRequired: Boolean,
    val returnQuarantineRequired: Boolean,
    val returnQuarantineMessage: String,

    val borderStatusException: String,
    val openForVaccinated: Boolean,
    val unvaccinatedBorderStatus: String,
    val vaccinatedArrivalTestRequired: Boolean,
    val vaccinatedArrivalQuarantineRequired: Boolean,
    val vaccinatedReturnTestRequired: Boolean,
    val vaccinatedReturnQuarantineRequired: Boolean,
    val valid: Boolean
) {
    val statusSignId = when(borderStatus){
        "RESTRICTION" -> R.drawable.country_status_restriction
        "OPEN" -> R.drawable.country_status_open
        "CLOSED" -> R.drawable.country_status_closed
        else -> null
    }
}