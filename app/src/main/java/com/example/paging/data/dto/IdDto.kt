package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IdDto(
    @SerialName("name") val name: String? = null,
    @SerialName("value") val value: String? = null
)
