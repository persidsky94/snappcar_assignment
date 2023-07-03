@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.snappcar_assignment.presentation.main_composables

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.snappcar_assignment.domain.models.CarsSortOrder
import com.example.snappcar_assignment.domain.models.DistanceFilter

@Composable
fun <T> DropdownMenu(
	label: String,
	options: List<T>,
	selectedOption: T,
	optionToText: (T) -> String,
	onOptionSelected: (T) -> Unit,
	modifier: Modifier
) {
	var expanded by remember { mutableStateOf(false) }

	ExposedDropdownMenuBox(
		modifier = modifier,
		expanded = expanded,
		onExpandedChange = { expanded = !expanded },
	) {
		TextField(
			modifier = Modifier.menuAnchor(),
			readOnly = true,
			value = optionToText(selectedOption),
			onValueChange = {},
			label = { Text(label) },
			trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
			colors = ExposedDropdownMenuDefaults.textFieldColors(),
		)
		ExposedDropdownMenu(
			expanded = expanded,
			onDismissRequest = { expanded = false },
		) {
			options.forEach { option ->
				DropdownMenuItem(
					text = { Text(optionToText(option)) },
					onClick = {
						expanded = false
						onOptionSelected(option)
					},
					contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
				)
			}
		}
	}
}

@Composable
fun DistanceFilterDropdown(
	selected: DistanceFilter,
	onOptionSelected: (DistanceFilter) -> Unit,
	modifier: Modifier
) {
	DropdownMenu(
		label = "Max distance",
		options = DistanceFilter.values().toList(),
		selectedOption = selected,
		optionToText = { option -> "${option.maxDistanceMeters} m" },
		onOptionSelected = onOptionSelected,
		modifier = modifier
	)
}

@Composable
fun CarsSortOrderDropdown(
	selected: CarsSortOrder,
	onOptionSelected: (CarsSortOrder) -> Unit,
	modifier: Modifier
) {
	DropdownMenu(
		label = "Sort order",
		options = CarsSortOrder.values().toList(),
		selectedOption = selected,
		optionToText = { option -> "${option.value.capitalize()}" },
		onOptionSelected = onOptionSelected,
		modifier = modifier
	)
}