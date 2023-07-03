package com.example.snappcar_assignment.domain.models

enum class DistanceFilter(val maxDistanceMeters: Int) {
	Close(3000),
	Medium(5000),
	Far(7000)
}