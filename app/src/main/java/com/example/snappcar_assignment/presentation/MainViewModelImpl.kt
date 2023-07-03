package com.example.snappcar_assignment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.models.DistanceFilter
import com.example.snappcar_assignment.domain.usecases.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class MainViewModelImpl(
	private val dispatcherIO: CoroutineDispatcher = Dispatchers.IO,
	private val dispatcherComputation: CoroutineDispatcher = Dispatchers.Default,
	private val citySearchUseCase: CitySearchUseCase = CitySearchUseCaseImpl(),
	private val carsSearchUseCaseImpl: CarsSearchUseCase = CarsSearchUseCaseImpl()
): MainViewModel,
	CitySearchUseCase by citySearchUseCase,
	ViewModel()
{
	private val _distanceFilter = MutableStateFlow(DistanceFilter.Close)
	override val distanceFilter = _distanceFilter
	
	private val _sortOrder = MutableStateFlow(CarsSortOrder.Recommended)
	override val sortOrder = _sortOrder
	
	private val _carsState = MutableStateFlow<CarsState>(CarsState.NoCars)
	override val carsState = _carsState
	
	init {
		viewModelScope.launch {
			combine(_distanceFilter, _sortOrder, citySearchUseCase.citySearchState) {distance, sort, city ->
				if (city is CitySearchState.SelectedCity) {
					val carsFlow = carsSearchUseCaseImpl.searchForCars(
						city = city.city,
						distanceFilter = distance,
						sortOrder = sort
					)
					
					_carsState.emit(
						CarsState.FoundCars(carsFlow)
					)
				}
			}.collect()
		}
	}
	
	override fun onNewUserSearchInput(input: String) {
		viewModelScope.launch {
			_carsState.emit(CarsState.NoCars)
			citySearchUseCase.onUserSearchTermChanged(input)
		}
	}
	
	override fun onCitySelectedByUser(city: City) {
		viewModelScope.launch { citySearchUseCase.onCitySelected(city) }
	}
	
	override fun onDistanceFilterSelected(filter: DistanceFilter) {
		viewModelScope.launch { _distanceFilter.emit(filter) }
	}
	
	override fun onCarsSortOrderSelected(sortOrder: CarsSortOrder) {
		viewModelScope.launch { _sortOrder.emit(sortOrder) }
	}
}