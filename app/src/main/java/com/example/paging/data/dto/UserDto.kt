package com.example.paging.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("gender") val gender: String? = null,
    @SerialName("name") val name: NameDto? = null,
    @SerialName("location") val location: LocationDto? = null,
    @SerialName("email") val email: String? = null,
    @SerialName("login") val login: LoginDto? = null,
    @SerialName("dob") val dob: DobDto? = null,
    @SerialName("registered") val registered: RegisteredDto? = null,
    @SerialName("phone") val phone: String? = null,
    @SerialName("cell") val cell: String? = null,
    @SerialName("id") val id: IdDto? = null,
    @SerialName("picture") val picture: PictureDto? = null,
    @SerialName("nat") val nat: String? = null
)
