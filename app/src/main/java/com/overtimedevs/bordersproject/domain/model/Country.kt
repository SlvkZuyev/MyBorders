package com.overtimedevs.bordersproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.overtimedevs.bordersproject.data.country.*

@Entity
class Country(

    @PrimaryKey
    val countryId : Int,
    val isLiked: Boolean,
    val lastModified : Int,
    val borderStatus : String,
    val countryName : String,
    val countryGroupCode : String,
    val countryGroupName : String,
    val arrivalTestRequired : Boolean,
    val returnTestRequired : Boolean,
    val activeCases : Int,
    val restaurantStatus : String,
    val barStatus : String,
    val maskStatus : String,
    val arrivalTestStatus : String,
    val arrivalQuarantineStatus : Boolean,
    val arrivalQuarantineMessage : String,
    val returnTestStatus : Boolean,
    val returnTestMessage : String,
    val returnQuarantineStatus : Boolean,
    val returnQuarantineMessage : Boolean,
    val arrivalDocumentation : List<String>,
    val returnDocumentation : List<ReturnDocumentation>,
    val borderStatusException : String,
    val openForVaccinated : Boolean,
    val unvaccinatedBorderStatus : String,
    val vaccinatedArrivalTestRequired : Boolean,
    val vaccinatedArrivalQuarantineRequired : Boolean,
    val vaccinatedReturnTestRequired : Boolean,
    val vaccinatedReturnQuarantineRequired : Boolean,
    val currentWeekVaccinatedPercent : Double,
    val previousWeekVaccinatedPercent : Double,
    val returnQuarantineRequired : Boolean,
    val arrivalQuarantineRequired : Boolean,
    val valid : Boolean) {

}