package com.example.snappcar_assignment.data.dto

data class SearchRequest(
	val limit: Int,
	val offset: Int,
	val country: String,
	val lat: Float,
	val lng: Float,
	val max_distance: Int,
	val sort: String
)