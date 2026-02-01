package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InfoDto(
    @SerialName("seed") val seed: String? = null,
    @SerialName("results") val results: Int? = null,
    @SerialName("page") val page: Int? = null,
    @SerialName("version") val version: String? = null
)
