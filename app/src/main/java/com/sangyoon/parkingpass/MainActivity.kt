package com.sangyoon.parkingpass

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sangyoon.parkingpass.model.Car
import com.sangyoon.parkingpass.ui.screens.CarListScreen
import com.sangyoon.parkingpass.ui.screens.PassListScreen
import com.sangyoon.parkingpass.ui.screens.PassScreen
import com.sangyoon.parkingpass.ui.screens.SettingScreen
import com.sangyoon.parkingpass.ui.theme.ParkingPassTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParkingPassTheme {
                val navController = rememberNavController()

                val passList = remember {
                    mutableStateListOf(
                        Car(
                            id = 1,
                            number = "123가4568",
                            carType = "쏘나타",
                            ownerName = "홍길동",
                            ownerPhoneNumber = "010-1234-5678"
                        ), Car(
                            id = 2,
                            number = "123가4569",
                            carType = "그랜저",
                            ownerName = "홍길동",
                            ownerPhoneNumber = "010-1234-5678"
                        ), Car(
                            id = 3,
                            number = "123가4567",
                            carType = "아반떼",
                            ownerName = "홍길동",
                            ownerPhoneNumber = "010-1234-5678"
                        )
                    )
                }

                Scaffold(
                    bottomBar = { NavigationBar(navController = navController) }) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(
                            navController = navController,
                            startDestination = NavigationItem.Pass.screenRoute
                        ) {
                            composable(NavigationItem.Pass.screenRoute) {
                                PassListScreen(
                                    navController = navController,
                                    onPassClick = { passId ->
                                        navController.navigate("car/$passId")
                                    }
                                )
                            }
                            composable(NavigationItem.Car.screenRoute) {
                                CarListScreen(
                                    navController = navController
                                )
                            }
                            composable(NavigationItem.Setting.screenRoute) {
                                SettingScreen(
                                    navController = navController
                                )
                            }
                            composable("car/{carId}") { backStackEntry ->
                                val carId = backStackEntry.arguments?.getString("carId") ?: "1";
                                PassScreen(
                                    navController = navController, car = passList.first { car ->
                                        car.id == carId.toInt()
                                    })
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationBar(
    navController: NavController
) {
    val items = listOf(
        NavigationItem.Pass,
        NavigationItem.Car,
        NavigationItem.Setting
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground
    ) {
        items.forEach { item ->
            val nacBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = nacBackStackEntry?.destination?.route

            NavigationBarItem(
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)

                    )
                },
                label = {
                    Text(stringResource(id = item.title), fontSize = 9.sp)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

sealed class NavigationItem(
    val title: Int,
    val icon: Int,
    val screenRoute: String
) {
    data object Pass : NavigationItem(R.string.tab_pass, R.drawable.icon_pass, Const.PASS)
    data object Car : NavigationItem(R.string.tab_car, R.drawable.icon_car, Const.CAR)
    data object Setting :
        NavigationItem(R.string.tab_setting, R.drawable.icon_setting, Const.SETTING)
}