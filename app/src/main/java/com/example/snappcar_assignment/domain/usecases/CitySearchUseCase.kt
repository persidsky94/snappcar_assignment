package com.example.snappcar_assignment.domain.usecases

import com.example.snappcar_assignment.domain.models.City
import kotlinx.coroutines.flow.StateFlow

interface CitySearchUseCase {
	val citySearchState: StateFlow<CitySearchState>
	
	suspend fun onUserSearchTermChanged(newSearchTerm: String)
	
	suspend fun onCitySelected(city: City)
	suspend fun onCityDeselected()
}

sealed class CitySearchState {
	data class SelectedCity(val city: City): CitySearchState()
	data class NotSelectedCity(val searchPrefix: String, val suggestions: List<City>): CitySearchState()
}

fun CitySearchState.currentInput(): String = when (this) {
	is CitySearchState.SelectedCity -> city.name
	is CitySearchState.NotSelectedCity -> searchPrefix
}