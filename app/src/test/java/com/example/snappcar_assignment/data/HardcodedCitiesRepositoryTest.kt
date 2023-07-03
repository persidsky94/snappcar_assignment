package com.example.snappcar_assignment.data

import com.example.snappcar_assignment.data.repos.HardcodedCitiesRepository
import com.example.snappcar_assignment.domain.models.CITIES_SORTED_BY_NAMES
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class HardcodedCitiesRepositoryTest(
	private val prefix: String,
	private val expectedCitiesNames: List<String>
) {
	
	companion object {
		@JvmStatic
		@Parameterized.Parameters
		fun data() = listOf(
			arrayOf("Am", listOf("Amberg", "Amersfoort", "Amstelveen", "Amsterdam")), //multiple cities
			arrayOf("Amsterdam", listOf("Amsterdam")), //single city
			arrayOf("nonexistent", listOf<String>()), //no cities
			arrayOf("123", listOf<String>()), //digits - no cities
		  arrayOf("beRl", listOf("Berlin")), // case-insensitive
			arrayOf("", CITIES_SORTED_BY_NAMES.map { it.name }), //empty string - all entries
			arrayOf("Düss", listOf("Düsseldorf")), //special symbols
			arrayOf( //first element - no troubles with indices
				CITIES_SORTED_BY_NAMES.first().name,
				listOf(CITIES_SORTED_BY_NAMES.first().name)
			),
			arrayOf( //last element - no troubles with indices
				CITIES_SORTED_BY_NAMES.last().name,
				listOf(CITIES_SORTED_BY_NAMES.last().name)
			),
		)
	}
	
	@Test
	fun positive_citiesStartingWith() {
		//given
		val citiesRepository = HardcodedCitiesRepository()
		
		//when
		val citiesStartingWithPrefix = citiesRepository.citiesStartingWith(prefix)
		
		//then
		assertEquals(
			expectedCitiesNames,
			citiesStartingWithPrefix.map { it.name }
		)
	}
	
}