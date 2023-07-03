package com.example.snappcar_assignment

import com.example.snappcar_assignment.data.repos.HardcodedCitiesRepository
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
	@Test
	fun addition_isCorrect() {
		assertEquals(4, 2 + 2)
	}
	
	@Test
	fun citiesRepo() {
		//given
		val citiesRepository = HardcodedCitiesRepository()
		val prefix = "Am"
		
		//when
		val citiesStartingWithPrefix = citiesRepository.citiesStartingWith(prefix)
		
		//then
		assertEquals(
			listOf("Amberg", "Amersfoort", "Amstelveen", "Amsterdam"),
			citiesStartingWithPrefix.map { it.name }
		)
	}
}