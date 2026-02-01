package com.example.paging.data.mapper

import com.example.paging.data.dto.*
import com.example.paging.domain.model.*

/**
 * Mapper functions to convert DTOs to domain models.
 */

fun UserDto.toDomain(): User {
    return User(
        uuid = login?.uuid.orEmpty(),
        gender = gender.orEmpty(),
        name = name?.toDomain() ?: Name("", "", ""),
        location = location?.toDomain() ?: Location(
            street = Street(0, ""),
            city = "",
            state = "",
            country = "",
            postcode = "",
            coordinates = Coordinates(0.0, 0.0),
            timezone = Timezone("", "")
        ),
        email = email.orEmpty(),
        phone = phone.orEmpty(),
        cell = cell.orEmpty(),
        dateOfBirth = dob?.toDomain() ?: DateInfo("", 0),
        registered = registered?.toDomain() ?: DateInfo("", 0),
        picture = picture?.toDomain() ?: Picture("", "", ""),
        nationality = nat.orEmpty()
    )
}

fun NameDto.toDomain(): Name {
    return Name(
        title = title.orEmpty(),
        first = first.orEmpty(),
        last = last.orEmpty()
    )
}

fun LocationDto.toDomain(): Location {
    return Location(
        street = street?.toDomain() ?: Street(0, ""),
        city = city.orEmpty(),
        state = state.orEmpty(),
        country = country.orEmpty(),
        coordinates = coordinates?.toDomain() ?: Coordinates(0.0, 0.0),
        timezone = timezone?.toDomain() ?: Timezone("", "")
    )
}

fun StreetDto.toDomain(): Street {
    return Street(
        number = number ?: 0,
        name = name.orEmpty()
    )
}

fun CoordinatesDto.toDomain(): Coordinates {
    return Coordinates(
        latitude = latitude?.toDoubleOrNull() ?: 0.0,
        longitude = longitude?.toDoubleOrNull() ?: 0.0
    )
}

fun TimezoneDto.toDomain(): Timezone {
    return Timezone(
        offset = offset.orEmpty(),
        description = description.orEmpty()
    )
}

fun DobDto.toDomain(): DateInfo {
    return DateInfo(
        date = date.orEmpty(),
        age = age ?: 0
    )
}

fun RegisteredDto.toDomain(): DateInfo {
    return DateInfo(
        date = date.orEmpty(),
        age = age ?: 0
    )
}

fun PictureDto.toDomain(): Picture {
    return Picture(
        large = large.orEmpty(),
        medium = medium.orEmpty(),
        thumbnail = thumbnail.orEmpty()
    )
}

fun RandomUserResponse.toDomain(): List<User> {
    return results.map { it.toDomain() }
}
