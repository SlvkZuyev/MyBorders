package com.overtimedevs.bordersproject.data.data_source

import com.overtimedevs.bordersproject.domain.model.Country
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class CountryRemoteDataSource {

    fun getCountries(): Observable<List<Country>> {
        val arrayList = listOf(
            Country(
                1,
                false,
                0,
                "RESTRICTION",
                "Country 1",
                false,
                false,
                "arrival message",
                false,
                false,
                "return quar meesage",
                "border ex",
                false,
                "unvac border status",
                false,
                false,
                false,
                false,
                true
            ),
            Country(
                2,
                false,
                0,
                "OPEN",
                "Country 2",
                false,
                false,
                "arrival message",
                false,
                false,
                "return quar meesage",
                "border ex",
                false,
                "unvac border status",
                false,
                false,
                false,
                false,
                true
            ),
            Country(
                3,
                false,
                0,
                "CLOSED",
                "Country 3",
                false,
                false,
                "arrival message",
                false,
                false,
                "return quar meesage",
                "border ex",
                false,
                "unvac border status",
                false,
                false,
                false,
                false,
                true
            ),
            Country(
                4,
                false,
                0,
                "OPEN",
                "Country 4",
                false,
                false,
                "arrival message",
                false,
                false,
                "return quar meesage",
                "border ex",
                false,
                "unvac border status",
                false,
                false,
                false,
                false,
                true
            ))
            return Observable.just(arrayList).delay(2, TimeUnit.SECONDS)
    }

    fun saveCountries(){

    }
}