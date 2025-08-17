package com.sangyoon.parkingpass.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sangyoon.parkingpass.model.Car
import com.sangyoon.parkingpass.viewmodel.PassListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassScreen(
    navController: NavController,
    viewModel: PassListViewModel,
    carId: String
) {
    LaunchedEffect(carId) {

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("출차 기록") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier.padding(16.dp)
            ) {

            }
        }
    }
}