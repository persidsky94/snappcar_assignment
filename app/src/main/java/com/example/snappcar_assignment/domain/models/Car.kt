package com.example.snappcar_assignment.domain.models

data class Car(
	val id: String,
	val model: String,
	val year: Int,
	val gear: String,
	val fuelType: String,
	val photos: List<String>,
	val isKeyless: Boolean,
	val distance: Double,
	val addressStreet: String,
	val price: Double,
	val pricePerKilometer: Double,
	val freeKilometersPerDay: Int
)