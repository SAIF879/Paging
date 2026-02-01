package com.example.paging.domain.model

/**
 * Domain model representing a user.
 */
data class User(
    val uuid: String,
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val phone: String,
    val cell: String,
    val dateOfBirth: DateInfo,
    val registered: DateInfo,
    val picture: Picture,
    val nationality: String
)

data class Name(
    val title: String,
    val first: String,
    val last: String
) {
    val fullName: String get() = "$title $first $last"
}

data class Location(
    val street: Street,
    val city: String,
    val state: String,
    val country: String,
    val coordinates: Coordinates,
    val timezone: Timezone
) {
    val fullAddress: String get() = "${street.fullStreet}, $city, $state, $country "
}

data class Street(
    val number: Int,
    val name: String
) {
    val fullStreet: String get() = "$number $name"
}

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class Timezone(
    val offset: String,
    val description: String
)

data class DateInfo(
    val date: String,
    val age: Int
)

data class Picture(
    val large: String,
    val medium: String,
    val thumbnail: String
)
