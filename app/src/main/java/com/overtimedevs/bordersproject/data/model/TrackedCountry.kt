package com.overtimedevs.bordersproject.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TrackedCountry (
    @PrimaryKey
    val countryId: Int)
{
}