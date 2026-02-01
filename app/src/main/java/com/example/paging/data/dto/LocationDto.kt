package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("street") val street: StreetDto? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("state") val state: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("coordinates") val coordinates: CoordinatesDto? = null,
    @SerialName("timezone") val timezone: TimezoneDto? = null
)
