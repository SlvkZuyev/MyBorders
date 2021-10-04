package com.overtimedevs.bordersproject.data.data_source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TrackedCountry (
    @PrimaryKey
    val countryId: Int)
{
}