package com.overtimedevs.bordersproject.data.repository

import com.overtimedevs.bordersproject.data.data_source.CountryDao
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.repositiory.CountryRepository
import kotlinx.coroutines.flow.Flow

class CountryRepositoryImpl(private val dao: CountryDao) : CountryRepository {

    override fun getCountries(): Flow<List<Country>> {
        return dao.getAll()
    }

    override suspend fun getCountryById(id: Int): Country? {
        return dao.getById(id)
    }

    override suspend fun insertCountry(country: Country) {
        return dao.insert(country)
    }

    override suspend fun deleteCountry(country: Country) {
        return dao.delete(country)
    }
}