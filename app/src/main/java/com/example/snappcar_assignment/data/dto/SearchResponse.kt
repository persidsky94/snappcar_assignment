package com.example.snappcar_assignment.data.dto

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("searchId")
    val searchId: String,
    @SerializedName("sums")
    val sums: Sums
)

data class Result(
    @SerializedName("car")
    val car: CarDTO,
    @SerializedName("ci")
    val ci: String,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("flags")
    val flags: Flags,
    @SerializedName("priceInformation")
    val priceInformation: PriceInformation,
    @SerializedName("user")
    val user: User
)

data class Sums(
    @SerializedName("totalResults")
    val totalResults: Int
)

data class CarDTO(
    @SerializedName("accessories")
    val accessories: List<String>,
    @SerializedName("address")
    val address: Address,
    @SerializedName("allowed")
    val allowed: List<String>,
    @SerializedName("bodyType")
    val bodyType: String,
    @SerializedName("carCategory")
    val carCategory: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("fuelType")
    val fuelType: String,
    @SerializedName("gear")
    val gear: String,
    @SerializedName("images")
    val images: List<String>,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("ownerId")
    val ownerId: String,
    @SerializedName("reviewAvg")
    val reviewAvg: Double,
    @SerializedName("reviewCount")
    val reviewCount: Int,
    @SerializedName("seats")
    val seats: Int,
    @SerializedName("year")
    val year: Int
)

data class Flags(
    @SerializedName("favorite")
    val favorite: Boolean,
    @SerializedName("instantBookable")
    val instantBookable: Boolean,
    @SerializedName("isKeyless")
    val isKeyless: Boolean,
    @SerializedName("new")
    val new: Boolean,
    @SerializedName("previouslyRented")
    val previouslyRented: Boolean
)

data class PriceInformation(
    @SerializedName("freeKilometersPerDay")
    val freeKilometersPerDay: Int,
    @SerializedName("isoCurrencyCode")
    val isoCurrencyCode: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("pricePerKilometer")
    val pricePerKilometer: Double,
    @SerializedName("rentalDays")
    val rentalDays: Int
)

data class User(
    @SerializedName("city")
    val city: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("imageUrl")
    val imageUrl: String,
    @SerializedName("street")
    val street: String
)

data class Address(
    @SerializedName("city")
    val city: String,
    @SerializedName("countryCode")
    val countryCode: String,
    @SerializedName("street")
    val street: String
)