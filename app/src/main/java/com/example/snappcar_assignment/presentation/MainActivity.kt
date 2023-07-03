package com.example.snappcar_assignment.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.snappcar_assignment.R
import com.example.snappcar_assignment.presentation.main_composables.CarsSearchResult
import com.example.snappcar_assignment.presentation.main_composables.SearchTopBar

class MainActivity : ComponentActivity() {
	
	private val vm: MainViewModel by viewModels<MainViewModelImpl>()
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Scaffold {
				Column {
					Column(
						modifier = Modifier
							.background(color = colorResource(R.color.snappcar_blue))
					) {
						SearchTopBar(vm = vm)
					}
					CarsSearchResult(
						vm = vm,
						modifier = Modifier
							.fillMaxSize()
					)
				}
			}
		}
	}
}