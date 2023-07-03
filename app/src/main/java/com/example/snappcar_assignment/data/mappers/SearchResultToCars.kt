package com.example.snappcar_assignment.data.mappers

import com.example.snappcar_assignment.data.dto.SearchResponse
import com.example.snappcar_assignment.domain.models.Car

class SearchResultMapper {
	fun mapSearchResponseToCars(searchResponse: SearchResponse): List<Car> {
		return searchResponse.results.map {
			Car(
				id = it.car.ownerId + it.car.createdAt,
				model = "${it.car.make} ${it.car.model}",
				year = it.car.year,
				gear = it.car.gear,
				fuelType = it.car.fuelType,
				photos = it.car.images,
				isKeyless = it.flags.isKeyless,
				distance = it.distance,
				addressStreet = it.car.address.street,
				price = it.priceInformation.price,
				pricePerKilometer = it.priceInformation.pricePerKilometer,
				freeKilometersPerDay = it.priceInformation.freeKilometersPerDay
			)
		}
	}
}
