package com.sangyoon.parkingpass.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sangyoon.parkingpass.model.Car
import com.sangyoon.parkingpass.viewmodel.PassListViewModel
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassListScreen(
    navController: NavController,
    viewModel: PassListViewModel,
    onPassClick: (String) -> Unit
) {
    val passList by viewModel.passList.collectAsState()
    /*val passList = listOf(
        Car(
            id = "1",
            plateNumber = "123가4568",
            carType = "쏘나타",
            ownerName = "홍길동",
            ownerPhoneNumber = "010-1234-5678",
            notes = "",
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ), Car(
            id = "2",
            plateNumber = "123가4569",
            carType = "그랜저",
            ownerName = "홍길동",
            ownerPhoneNumber = "010-1234-5678",
            notes = "",
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ), Car(
            id = "3",
            plateNumber = "123가4567",
            carType = "아반떼",
            ownerName = "홍길동",
            ownerPhoneNumber = "010-1234-5678",
            notes = "",
            isActive = true,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    )*/

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(passList) { pass ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPassClick(pass.id) }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = pass.plateNumber)
                    Spacer(Modifier.height(16.dp))
                    Text(text = pass.direction.toString())
                    Spacer(Modifier.height(16.dp))
                    Text(text = pass.timestampMillis.toString())
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PassListScreenPreview() {
    PassListScreen(
        navController = rememberNavController(),
        viewModel = viewModel(),
        onPassClick = {}
    )
}