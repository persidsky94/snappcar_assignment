package com.example.snappcar_assignment.domain.usecases

import androidx.paging.PagingData
import com.example.snappcar_assignment.data.repos.CarsSearchRepositoryImpl
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.models.DistanceFilter
import com.example.snappcar_assignment.domain.repos.CarsSearchRepository
import kotlinx.coroutines.flow.Flow

class CarsSearchUseCaseImpl(
	private val carsSearchRepository: CarsSearchRepository = CarsSearchRepositoryImpl()
) : CarsSearchUseCase {
	override suspend fun searchForCars(
		city: City,
		sortOrder: CarsSortOrder,
		distanceFilter: DistanceFilter
	): Flow<PagingData<Car>> {
		return carsSearchRepository.searchForCars(
			city = city,
			sortOrder = sortOrder,
			distanceFilter = distanceFilter
		)
	}
}