package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimezoneDto(
    @SerialName("offset") val offset: String? = null,
    @SerialName("description") val description: String? = null
)
