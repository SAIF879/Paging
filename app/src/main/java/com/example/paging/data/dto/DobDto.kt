package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DobDto(
    @SerialName("date") val date: String? = null,
    @SerialName("age") val age: Int? = null
)
