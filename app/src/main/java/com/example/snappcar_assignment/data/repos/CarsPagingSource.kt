package com.example.snappcar_assignment.data.repos

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.snappcar_assignment.data.mappers.SearchResultMapper
import com.example.snappcar_assignment.data.services.CarsSearchService
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.models.DistanceFilter


class CarsPagingSource(
	private val carsSearchService: CarsSearchService,
	private val limit: Int,
	private val responseMapper: SearchResultMapper,
	private val requestParams: CarsSearchRequestParams
): PagingSource<Int, Car>() {
	override fun getRefreshKey(state: PagingState<Int, Car>): Int? {
		return state.anchorPosition?.let { anchorPosition ->
			val page = state.closestPageToPosition(anchorPosition)
			page?.prevKey?.plus(limit)
				?: page?.nextKey?.minus(limit)
		}
	}
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Car> {
		return try {
			val offset = params.key ?: 0
			val response = carsSearchService.searchCars(
				limit = limit,
				offset = offset,
				country = requestParams.city.countryCode,
				lat = requestParams.latitude,
				lng = requestParams.longitude,
				maxDistance = requestParams.distanceFilter.maxDistanceMeters,
				sort = requestParams.sortOrder.value
			)
			
			val cars = responseMapper.mapSearchResponseToCars(response)
			
			LoadResult.Page(
				data = cars,
				prevKey = if (offset == 0) null else offset.minus(limit),
				nextKey = if (cars.isEmpty()) null else offset.plus(limit),
			)
		} catch (e: Exception) {
			LoadResult.Error(e)
		}
	}
}

data class CarsSearchRequestParams(
	val longitude: Float,
	val latitude: Float,
	val sortOrder: CarsSortOrder,
	val distanceFilter: DistanceFilter,
	val city: City
)