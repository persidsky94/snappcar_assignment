package com.example.snappcar_assignment.data.repos

import com.example.snappcar_assignment.domain.models.CITIES_SORTED_BY_NAMES
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.repos.CitiesRepository

class HardcodedCitiesRepository(
	private val sortedCities: List<City> = CITIES_SORTED_BY_NAMES
) : CitiesRepository {
	override fun citiesStartingWith(prefix: String): List<City> {
		val lowercasePrefix = prefix.lowercase()
		val firstIndex = sortedCities.binarySearch { it.lowercaseName.compareTo(lowercasePrefix) }
		val actualFirstIndex = if (firstIndex < 0) -firstIndex-1 else firstIndex
		val secondIndex = sortedCities.binarySearch(fromIndex = actualFirstIndex) {
			val minLen = minOf(lowercasePrefix.length, it.lowercaseName.length)
			val shortPrefix = lowercasePrefix.take(minLen)
			val shortName = it.lowercaseName.take(minLen)
			if (shortName > shortPrefix) 1 else -1
		}
		val actualSecondIndex = if (secondIndex < 0) -secondIndex-1 else secondIndex
		val sublist = sortedCities.subList(fromIndex = actualFirstIndex, toIndex = actualSecondIndex)
		return sublist
	}
}