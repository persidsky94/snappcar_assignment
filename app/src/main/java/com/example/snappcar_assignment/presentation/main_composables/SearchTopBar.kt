package com.example.snappcar_assignment.presentation.main_composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.example.snappcar_assignment.domain.models.City
import com.example.snappcar_assignment.domain.usecases.CitySearchState
import com.example.snappcar_assignment.domain.usecases.currentInput
import com.example.snappcar_assignment.presentation.MainViewModel

@Composable
fun SearchTopBar(vm: MainViewModel) {
	CitySearchBar(vm)
	SearchParams(vm)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun CitySearchBar(vm: MainViewModel) {
	var active by remember { mutableStateOf(false) }
	
	val state = vm.citySearchState.collectAsState()
	val cityState = state.value
	
	SearchBar(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentHeight()
			.padding(PaddingValues(horizontal = 16.dp, vertical = 4.dp)),
		query = cityState.currentInput(),
		onQueryChange = vm::onNewUserSearchInput,
		onSearch = {},
		active = active,
		onActiveChange = { active = it },
		placeholder = {
			Text(
				text = "Select city",
				color = Color.Gray
			)
		},
		shape = RoundedCornerShape(2.dp),
		leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search icon") },
		trailingIcon = {
			Icon(
				modifier = Modifier.clickable { vm.onNewUserSearchInput("") },
				imageVector = Icons.Default.Close,
				contentDescription = "close icon"
			)
		}
	) {
		if (cityState is CitySearchState.NotSelectedCity) {
			Suggestions(
				suggestions = cityState.suggestions,
				onCitySelected = { city ->
					vm.onCitySelectedByUser(city)
					active = false
				}
			)
		}
	}
}

@Composable
private fun SearchParams(vm: MainViewModel) {
	val filter = vm.distanceFilter.collectAsState()
	val sortOrder = vm.sortOrder.collectAsState()
	
	Row(modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)) {
		val modifierForDropdowns = Modifier
			.weight(0.5f)
			.padding(4.dp)
		
		DistanceFilterDropdown(
			selected = filter.value,
			onOptionSelected = vm::onDistanceFilterSelected,
			modifier = modifierForDropdowns
		)
		CarsSortOrderDropdown(
			selected = sortOrder.value,
			onOptionSelected = vm::onCarsSortOrderSelected,
			modifier = modifierForDropdowns
		)
	}
}

@Composable
private fun Suggestions(
	suggestions: List<City>,
	onCitySelected: (City) -> Unit,
) {
	LazyColumn {
		items(suggestions.size) {
			val city = suggestions[it]
			ClickableText(
				modifier = Modifier
					.padding(8.dp)
					.fillMaxWidth()
					.wrapContentWidth(align = Alignment.CenterHorizontally),
				text = AnnotatedString("${city.name}, ${city.countryCode}"),
				onClick = {
					onCitySelected(city)
				}
			)
		}
	}
}