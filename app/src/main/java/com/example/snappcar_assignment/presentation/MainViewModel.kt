package com.example.snappcar_assignment.presentation

import androidx.paging.PagingData
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.usecases.CitySearchUseCase
import com.example.snappcar_assignment.domain.models.DistanceFilter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MainViewModel: CitySearchUseCase {
	val distanceFilter: StateFlow<DistanceFilter>
	val sortOrder: StateFlow<CarsSortOrder>
	val carsState: StateFlow<CarsState>
	
	fun onNewUserSearchInput(input: String)
	fun onCitySelectedByUser(city: City)
	
	fun onDistanceFilterSelected(filter: DistanceFilter)
	fun onCarsSortOrderSelected(sortOrder: CarsSortOrder)
}

sealed class CarsState {
	object NoCars : CarsState()
	data class FoundCars(val foundCars: Flow<PagingData<Car>>): CarsState()
}

