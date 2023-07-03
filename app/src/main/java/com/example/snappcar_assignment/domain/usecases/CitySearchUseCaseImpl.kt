package com.example.snappcar_assignment.domain.usecases

import com.example.snappcar_assignment.domain.repos.CitiesRepository
import com.example.snappcar_assignment.data.repos.HardcodedCitiesRepository
import com.example.snappcar_assignment.domain.models.City
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class CitySearchUseCaseImpl(
	private val scope: CoroutineScope = CoroutineScope(Dispatchers.Default),
	private val citiesRepository: CitiesRepository = HardcodedCitiesRepository(),
	private val duration: Duration = 500.milliseconds,
) : CitySearchUseCase {
	
	private val currentUserSearchTerm = MutableSharedFlow<String>()
	
	private val suggestions: Flow<List<City>> = flow {
		emit(emptyList())
		emitAll(
			currentUserSearchTerm
				.debounce(duration)
				.map { lastUserSearchTerm ->
					citiesRepository.citiesStartingWith(lastUserSearchTerm)
				}
		)
	}
	
	private val lastSelectedCity = MutableSharedFlow<CitySearchState.SelectedCity>()
	private val lastNotSelectedCity: Flow<CitySearchState> = combine(currentUserSearchTerm, suggestions) { term, sugg ->
		CitySearchState.NotSelectedCity(searchPrefix = term, suggestions = sugg)
	}
	
	private val _citySearchState = MutableStateFlow<CitySearchState>(
		CitySearchState.NotSelectedCity(
			"",
			emptyList()
		)
	)
	override val citySearchState: StateFlow<CitySearchState> = _citySearchState
	
	
	init {
		listOf(lastSelectedCity, lastNotSelectedCity).forEach { flow ->
			scope.launch {
				flow.collect { _citySearchState.emit(it) }
			}
		}
	}
	
	override suspend fun onUserSearchTermChanged(newSearchTerm: String) {
		currentUserSearchTerm.emit(newSearchTerm)
	}
	
	override suspend fun onCitySelected(city: City) {
		lastSelectedCity.emit(CitySearchState.SelectedCity(city))
	}
	
	override suspend fun onCityDeselected() {
		val term = when (val state = _citySearchState.value) {
			is CitySearchState.SelectedCity -> state.city.name
			is CitySearchState.NotSelectedCity -> state.searchPrefix
		}
		currentUserSearchTerm.emit(term)
		_citySearchState.emit(CitySearchState.NotSelectedCity(term, emptyList()))
	}
}