package com.overtimedevs.bordersproject.extensions

import android.util.Log
import com.overtimedevs.bordersproject.data.model.country_response.CountryDto
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.presentation.model.CountryCardModel

//Todo clean this shit
fun CountryDto.toModel() = Country(
    countryId = countryId,
    isTracked = false,
    lastModified = lastModified,
    borderStatus = borderStatus,
    countryName = countryName,
    arrivalTestRequired = arrivalTestRequired,
    arrivalQuarantineRequired = arrivalQuarantineRequired,
    returnTestRequired = returnTestRequired,
    returnQuarantineRequired = returnQuarantineRequired,
    borderStatusException = borderStatusData.exceptions,
    openForVaccinated = openForVaccinated,
    unvaccinatedBorderStatus = unvaccinatedBorderStatus,
    vaccinatedArrivalTestRequired = vaccinatedArrivalTestRequired,
    vaccinatedReturnTestRequired = vaccinatedArrivalTestRequired,
    vaccinatedArrivalQuarantineRequired = vaccinatedArrivalQuarantineRequired,
    vaccinatedReturnQuarantineRequired = vaccinatedReturnQuarantineRequired,
    valid = valid,

    summary = getSummary(unvaccinatedBorderStatus, vaccinatedArrivalTestRequired, countryName),

    returnTestMessage = getTestMessage(returnTestStatus.status, returnTestStatus.messageId, countryName),
    returnTestExceptions = getExceptionMessage(returnTestStatus.exceptions),
    returnQuarantineExceptions = getExceptionMessage(returnQuarantineStatus.body),
    returnQuarantineMessage = getQuarantineMessage(returnQuarantineStatus.status, returnQuarantineStatus.messageId, countryName),

    arrivalTestMessage = getTestMessage(arrivalTestStatus.status, arrivalTestStatus.messageId, countryName),
    arrivalTestExceptions = getExceptionMessage(arrivalTestStatus.exceptions),
    arrivalQuarantineExceptions = getExceptionMessage(arrivalQuarantineStatus.body),
    arrivalQuarantineMessage = getQuarantineMessage(arrivalQuarantineStatus.status, arrivalQuarantineStatus.messageId, countryName)

)

fun getExceptionMessage(loadedMessage: String?): String{

    if(loadedMessage == null || loadedMessage == ""){
        return "No exceptions"
    } else {
        return htmlToString(loadedMessage)
    }

}

fun getTestMessage(status: String, messageId: String?, countryName: String) : String {
    return when (status) {
        "NAAT" -> {
            "Visitors must present a negative RT-PCR (NAAT) test taken $messageId hours before departing to $countryName."
        }
        "BOTH" -> {
            "Unvaccinated visitors must present a negative RT-PCR (NAAT) or Antigen (quick-test) test taken $messageId hours before departing to $countryName."
        }
        else -> {
            Log.d("DtoExtension", "mapping error: unknown status: $status")
            "Unknown status: $status"
        }
    }
}


fun getQuarantineMessage(isQuarantineRequared : Boolean, messageId : String?, countryName: String) : String{
    return if(isQuarantineRequared){
        "\tVisitors will need to quarantine for $messageId days upon entering $countryName."
    } else {
        if(messageId == null){
            "\tVisitors are not required to quarantine after entering $countryName."
        } else {
            "\tUnvaccinated visitors will need to quarantine for $messageId days upon entering $countryName."
        }
    }
}

//todo make readable
private fun getSummary(unvaccinatedBorderStatus: String?, vaccinatedArrivalTestRequired: Boolean, countryName: String) : String{
    return if(unvaccinatedBorderStatus == "CLOSED" ){
        if(vaccinatedArrivalTestRequired){
            "    Unvaccinated visitors will not be allowed to enter $countryName."
        } else {
            "    Unvaccinated visitors will not be allowed to enter $countryName.\n\n" +
            "    Fully vaccinated visitors can enter $countryName without restrictions."
        }

    } else if (unvaccinatedBorderStatus == "RESTRICTIONS") {
        if(vaccinatedArrivalTestRequired){
            "    Most visitors need to provide a negative COVID-19 test result to enter $countryName."
        } else{
            "    Unvaccinated visitors can enter $countryName with a negative COVID-19 test result.\n\n" +
            "    Fully vaccinated visitors can enter $countryName without restrictions."
        }
    } else if(unvaccinatedBorderStatus == "OPEN"){
        "    All visitors can enter $countryName without restrictions!"
    } else if(unvaccinatedBorderStatus == null) {
        "    Most visitors need to provide a negative COVID-19 test result to enter $countryName."
    }
    else {
        Log.d("DtoExtension", "mapping error: unknown summary. unvaccinatedBorderStatus: $unvaccinatedBorderStatus, vaccinatedArrivalTestRequired: $vaccinatedArrivalTestRequired")
        "    No data"
    }
}

fun Country.toCountryCard() : CountryCardModel {
    return CountryCardModel(
        countryId = countryId,
        borderStatus = borderStatus,
        countryName = countryName,
        message = summary,
        trackStatus = isTracked
    )
}

fun htmlToString(htmlString: String?): String{
    return htmlString?.replace(Regex("\\<[^>]*>"),"    ") ?: ""
}

