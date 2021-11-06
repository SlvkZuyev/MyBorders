package com.overtimedevs.bordersproject.domain.use_case.country

import com.overtimedevs.bordersproject.data.repository.CountryRepository
import com.overtimedevs.bordersproject.domain.model.Country
import com.overtimedevs.bordersproject.domain.use_case.country.CopyTrackStatuses
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllCountries @Inject constructor(private val countryRepository: CountryRepository) {
    val copyTrackStatuses = CopyTrackStatuses()

    operator fun invoke(countryCode: String, forceLoadFromRemote: Boolean): Observable<List<Country>> {
        return Observable
            .zip(countryRepository.getAllCountries(
                countryCode,
                forceLoadFromRemote
            ), countryRepository.getTrackedCountries(),
                {allCountries, trackedCountries ->
                    copyTrackStatuses(
                        from = trackedCountries,
                        to = allCountries)
                }
            )
    }

}