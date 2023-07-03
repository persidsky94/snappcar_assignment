package com.example.snappcar_assignment.presentation.main_composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W600
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.snappcar_assignment.domain.models.Car
import com.example.snappcar_assignment.presentation.CarsState
import com.example.snappcar_assignment.presentation.MainViewModel
import kotlin.math.roundToInt

@Composable
fun CarsSearchResult(vm: MainViewModel, modifier: Modifier) {
	val state = vm.carsState.collectAsState()
	
	when (val carsState = state.value) {
		is CarsState.NoCars -> {
			Box(modifier = modifier) {
				Text(
					modifier = Modifier.align(Alignment.Center),
					text = "Select city first"
				)
			}
		}
		is CarsState.FoundCars -> {
			FoundCarsContent(carsState = carsState)
		}
	}
}

@Composable
fun FoundCarsContent(carsState: CarsState.FoundCars) {
	val foundCars = carsState.foundCars.collectAsLazyPagingItems()
	
	LazyColumn {
		itemsPaging(
			items = foundCars,
			key = { it.id }
		) { car ->
			CarItem(car)
		}
		
		when (foundCars.loadState.refresh) { //FIRST LOAD
			is LoadState.Error -> {
				//TODO Error Item
			}
			is LoadState.Loading -> { // Loading UI
				item {
					RefreshLoadingItem(modifier = Modifier.fillParentMaxSize())
				}
				
			}
			else -> {}
		}
		
		when (foundCars.loadState.append) { // Pagination
			is LoadState.Error -> {
				//TODO Pagination Error Item
			}
			is LoadState.Loading -> { // Pagination Loading UI
				item {
					PaginationLoadingItem(modifier = Modifier.fillMaxWidth())
				}
			}
			else -> {}
		}
	}
}

@Composable
fun PaginationLoadingItem(modifier: Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(text = "Pagination Loading")
		
		CircularProgressIndicator(color = Color.Black)
	}
}

@Composable
fun RefreshLoadingItem(modifier: Modifier) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
	) {
		Text(
			modifier = Modifier
				.padding(8.dp),
			text = "Refresh Loading"
		)
		
		CircularProgressIndicator(color = Color.Black)
	}
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun CarItem(mbCar: Car?) {
	mbCar?.let { car ->
		Column {
			val photoUrl = car.photos.firstOrNull().toString()
			if (photoUrl.isNotEmpty()) {
				GlideImage(
					model = photoUrl,
					contentDescription = "car image",
					modifier = Modifier
						.fillMaxWidth()
						.height(300.dp)
				)
			} else {
				Image(
					imageVector = Icons.Default.Warning,
					contentDescription = "no car photo placeholder"
				)
			}
			
			Text(
				modifier = Modifier
					.padding(16.dp),
				text = car.model,
				fontSize = 20.sp,
				fontWeight = W600
			)
			Text(
				modifier = Modifier
					.padding(PaddingValues(start = 16.dp, top = 4.dp, end = 8.dp, bottom = 28.dp)),
				text = "${car.fuelType} - ${car.freeKilometersPerDay} free km - ${car.distance.roundToInt()}m"
			)
		}
	}
}

fun <T : Any> LazyListScope.itemsPaging(
	items: LazyPagingItems<T>,
	key: ((item: T) -> Any)? = null,
	itemContent: @Composable LazyItemScope.(value: T?) -> Unit
) {
	items(
		count = items.itemCount,
		key = if (key == null) null else { index ->
			val item = items.peek(index)
			if (item == null) {
				PagingPlaceholderKey(index)
			} else {
				key(item)
			}
		}
	) { index ->
		itemContent(items[index])
	}
}

private data class PagingPlaceholderKey(private val index: Int)