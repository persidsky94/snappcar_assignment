package com.example.snappcar_assignment.domain.repos

import com.example.snappcar_assignment.domain.models.City

interface CitiesRepository {
	fun citiesStartingWith(prefix: String): List<City>
}