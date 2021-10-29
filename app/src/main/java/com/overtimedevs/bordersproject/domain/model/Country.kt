package com.overtimedevs.bordersproject.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.overtimedevs.bordersproject.R

@Entity
class Country(

    @PrimaryKey
    val countryId: Int,
    var isTracked: Boolean = false,
    val lastModified: Long,
    val borderStatus: String = "No data",
    val countryName: String = "No data",
    val summary: String = "No data",

    //Arrival
    val arrivalTestRequired: Boolean,
    val arrivalTestMessage: String? = "No data",
    val arrivalTestExceptions: String? = "No data",
    val arrivalQuarantineRequired: Boolean,
    val arrivalQuarantineMessage: String? = "No data",
    val arrivalQuarantineExceptions: String? = "No data",

    //Return
    val returnTestRequired: Boolean,
    val returnTestMessage: String? = "No data",
    val returnTestExceptions: String? = "No data",
    val returnQuarantineRequired: Boolean,
    var returnQuarantineMessage: String? = "No data",
    var returnQuarantineExceptions: String? = "No data",

    val borderStatusException: String? = "No data",
    val openForVaccinated: Boolean,
    val unvaccinatedBorderStatus: String? = "No data",
    val vaccinatedArrivalTestRequired: Boolean,
    val vaccinatedArrivalQuarantineRequired: Boolean,
    val vaccinatedReturnTestRequired: Boolean,
    val vaccinatedReturnQuarantineRequired: Boolean,
    val valid: Boolean
) {
        @Ignore
        var statusSignId = when(borderStatus) {
            "RESTRICTIONS" -> R.drawable.country_status_restriction
            "OPEN" -> R.drawable.country_status_open
            "CLOSED" -> R.drawable.country_status_closed
            else -> null
        }

    override fun equals(other: Any?): Boolean {
        val otherCountry = other as Country
        return countryId == otherCountry.countryId &&
                lastModified == otherCountry.lastModified
    }



}