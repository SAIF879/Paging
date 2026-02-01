package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RandomUserResponse(
    @SerialName("results") val results: List<UserDto> = emptyList(),
    @SerialName("info") val info: InfoDto? = null
)
