package com.sangyoon.parkingpass.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sangyoon.parkingpass.model.Car
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PassListScreen(
    navController: NavController,
    onPassClick: (Int) -> Unit
) {
    val passList = listOf(
        Car(
            id = 1,
            number = "123가4568",
            carType = "쏘나타",
            ownerName = "홍길동",
            ownerPhoneNumber = "010-1234-5678",
            inDate = LocalDateTime.now(),
            outDate = LocalDateTime.now()
        ),
        Car(
            id = 1,
            number = "47루4340",
            carType = "벨로스터",
            ownerName = "채상윤",
            ownerPhoneNumber = "010-1234-5678",
            inDate = LocalDateTime.now(),
            outDate = LocalDateTime.now()
        ),
        Car(
            id = 1,
            number = "02우1138",
            carType = "아반떼",
            ownerName = "김이름",
            ownerPhoneNumber = "010-1234-5678",
            inDate = LocalDateTime.now(),
            outDate = LocalDateTime.now()
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "입출차기록") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
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
                        Text(text = pass.number)
                        Spacer(Modifier.height(16.dp))
                        Text(text = pass.carType)
                        Spacer(Modifier.height(16.dp))
                        Text(text = pass.ownerName)
                        Spacer(Modifier.height(16.dp))
                        Text(text = pass.ownerPhoneNumber)
                    }
                }
            }
        }
    }
}