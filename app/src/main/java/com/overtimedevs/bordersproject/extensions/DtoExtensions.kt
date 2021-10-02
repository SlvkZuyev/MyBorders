package com.overtimedevs.bordersproject.extensions

import com.overtimedevs.bordersproject.data.data_source.remote.model.CountryDto
import com.overtimedevs.bordersproject.domain.model.Country

fun CountryDto.toModel() = Country(
    countryId = countryId,
    isTracked = false,
    lastModified = lastModified,
    borderStatus = borderStatus ?: "NoData",
    countryName = countryName,
    arrivalTestRequired = arrivalTestRequired,
    arrivalQuarantineRequired = arrivalQuarantineRequired,
    arrivalQuarantineMessage = arrivalQuarantineStatus.body ?: "NoData",
    returnTestRequired = returnTestRequired,
    returnQuarantineRequired = returnQuarantineRequired,
    returnQuarantineMessage = returnQuarantineStatus.body,
    borderStatusException = borderStatusData.exceptions,
    openForVaccinated = openForVaccinated,
    unvaccinatedBorderStatus = unvaccinatedBorderStatus,
    vaccinatedArrivalTestRequired = vaccinatedArrivalTestRequired,
    vaccinatedReturnTestRequired = vaccinatedArrivalTestRequired,
    vaccinatedArrivalQuarantineRequired = vaccinatedArrivalQuarantineRequired,
    vaccinatedReturnQuarantineRequired = vaccinatedReturnQuarantineRequired,
    valid = valid
)