package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreetDto(
    @SerialName("number") val number: Int? = null,
    @SerialName("name") val name: String? = null
)
