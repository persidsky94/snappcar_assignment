package com.example.snappcar_assignment.data.services

import com.example.snappcar_assignment.data.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarsSearchService {
	@GET("v2/search")
	suspend fun searchCars(
		@Query("limit") limit: Int,
		@Query("offset") offset: Int,
		@Query("country") country: String,
		@Query("lat") lat: Float,
		@Query("lng") lng: Float,
		@Query("max-distance") maxDistance: Int,
		@Query("sort") sort: String,
	): SearchResponse
}
