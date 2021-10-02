package com.overtimedevs.bordersproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.overtimedevs.bordersproject.R

@Entity
class Country(

    @PrimaryKey
    val countryId: Int,
    val isTracked: Boolean = false,
    val lastModified: Long,
    val borderStatus: String = "No data",
    val countryName: String = "No data",

    //Arrival
    val arrivalTestRequired: Boolean,
    val arrivalQuarantineRequired: Boolean,
    val arrivalQuarantineMessage: String? = "No data",

    //Return
    val returnTestRequired: Boolean,
    val returnQuarantineRequired: Boolean,
    val returnQuarantineMessage: String = "No data",

    val borderStatusException: String? = "No data",
    val openForVaccinated: Boolean,
    val unvaccinatedBorderStatus: String? = "No data",
    val vaccinatedArrivalTestRequired: Boolean,
    val vaccinatedArrivalQuarantineRequired: Boolean,
    val vaccinatedReturnTestRequired: Boolean,
    val vaccinatedReturnQuarantineRequired: Boolean,
    val valid: Boolean
) {
    var statusSignId = R.drawable.country_status_restriction
    /*
     var statusSignId = when(borderStatus){
        "RESTRICTION" -> R.drawable.country_status_restriction
        "OPEN" -> R.drawable.country_status_open
        "CLOSED" -> R.drawable.country_status_closed
        else -> null
    }
     */

}