package com.example.snappcar_assignment.data.repos

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.snappcar_assignment.data.mappers.SearchResultMapper
import com.example.snappcar_assignment.data.services.CarsSearchService
import com.example.snappcar_assignment.data.services.CarsSearchServiceFactory
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.models.DistanceFilter
import com.example.snappcar_assignment.domain.repos.CarsSearchRepository
import kotlinx.coroutines.flow.Flow

class CarsSearchRepositoryImpl : CarsSearchRepository {
	override suspend fun searchForCars(
		city: City,
		sortOrder: CarsSortOrder,
		distanceFilter: DistanceFilter
	): Flow<PagingData<Car>> {
		return Pager(
			config = PagingConfig(pageSize = 10),
			pagingSourceFactory = {
				CarsPagingSource(
					carsSearchService = CarsSearchServiceFactory().create(),
					limit = 10,
					responseMapper = SearchResultMapper(),
					requestParams = CarsSearchRequestParams(
						longitude = city.lng,
						latitude = city.lat,
						city = city,
						sortOrder = sortOrder,
						distanceFilter = distanceFilter
					)
				)
			}
		).flow
	}
}