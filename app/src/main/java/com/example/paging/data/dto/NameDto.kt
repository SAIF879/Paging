package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    @SerialName("title") val title: String? = null,
    @SerialName("first") val first: String? = null,
    @SerialName("last") val last: String? = null
)
