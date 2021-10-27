package com.overtimedevs.bordersproject.domain.repositiory

import com.overtimedevs.bordersproject.domain.model.Country
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    fun getCountries() : List<Country>

    suspend fun getCountryById(id: Int): Country?

    suspend fun insertCountry(country: Country)

    suspend fun deleteCountry(country: Country)
}