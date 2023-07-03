package com.example.snappcar_assignment.data.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class CarsSearchServiceFactory {
	
	@JvmOverloads
	fun create(okHttpClient: OkHttpClient? = null): CarsSearchService = run {
		val retrofitBuilder = Retrofit.Builder()
//			.addCallAdapterFactory(CoroutineCallAdapterFactory())
			.baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
		
		okHttpClient?.let {
			retrofitBuilder.client(okHttpClient)
		}
		
		retrofitBuilder.build().create(CarsSearchService::class.java)
	}
	
	companion object {
		private const val baseUrl = "https://api.snappcar.nl/"
	}
}