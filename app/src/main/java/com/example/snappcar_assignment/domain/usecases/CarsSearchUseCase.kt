package com.example.snappcar_assignment.domain.usecases

import androidx.paging.PagingData
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.models.DistanceFilter
import kotlinx.coroutines.flow.Flow

interface CarsSearchUseCase {
	suspend fun searchForCars(
		city: City,
		sortOrder: CarsSortOrder,
		distanceFilter: DistanceFilter
	): Flow<PagingData<Car>>
}