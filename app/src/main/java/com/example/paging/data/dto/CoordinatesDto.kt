package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDto(
    @SerialName("latitude") val latitude: String? = null,
    @SerialName("longitude") val longitude: String? = null
)
