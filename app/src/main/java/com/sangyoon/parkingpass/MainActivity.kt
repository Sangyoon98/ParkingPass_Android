package com.sangyoon.parkingpass

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sangyoon.parkingpass.model.Car
import com.sangyoon.parkingpass.model.Direction
import com.sangyoon.parkingpass.model.Pass
import com.sangyoon.parkingpass.model.Source
import com.sangyoon.parkingpass.repository.FirebasePassRepository
import com.sangyoon.parkingpass.ui.screens.CarListScreen
import com.sangyoon.parkingpass.ui.screens.DialogScreen
import com.sangyoon.parkingpass.ui.screens.PassListScreen
import com.sangyoon.parkingpass.ui.screens.PassScreen
import com.sangyoon.parkingpass.ui.screens.SettingScreen
import com.sangyoon.parkingpass.ui.theme.ParkingPassTheme
import com.sangyoon.parkingpass.viewmodel.PassListViewModel
import com.sangyoon.parkingpass.viewmodel.ViewModelFactory
import java.time.LocalDateTime
import java.util.UUID

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ParkingPassTheme {
                val navController = rememberNavController()
                val passListViewModel: PassListViewModel = viewModel(
                    factory = ViewModelFactory(passRepository = FirebasePassRepository())
                )

                NavHost(
                    navController = navController,
                    startDestination = Const.MAIN
                ) {
                    composable(Const.MAIN) {
                        MainScreen(
                            passListViewModel = passListViewModel,
                            rootNavController = navController
                        )
                    }
                    composable("${Const.CAR}/{carId}") { backStackEntry ->
                        val carId = backStackEntry.arguments?.getString("carId") ?: "1";
                        PassScreen(
                            navController = navController,
                            viewModel = passListViewModel,
                            carId = carId
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    passListViewModel: PassListViewModel,
    rootNavController: NavController
) {
    val mainNavController = rememberNavController()
    val backStackEntry by mainNavController.currentBackStackEntryAsState()
    val route = backStackEntry?.destination?.route
    val openDialog = remember { mutableStateOf(false) }

    val title = when (route) {
        NavigationItem.Pass.screenRoute -> stringResource(R.string.tab_pass)
        NavigationItem.Car.screenRoute -> stringResource(R.string.tab_car)
        NavigationItem.Setting.screenRoute -> stringResource(R.string.tab_setting)
        else -> stringResource(R.string.app_name)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = title)
                }
            )
        },
        bottomBar = {
            NavigationBar(navController = mainNavController)
        },
        floatingActionButton = {
            if (route == NavigationItem.Pass.screenRoute) {
                FloatingActionButton(
                    onClick = { openDialog.value = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(id = R.string.button_add)
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = NavigationItem.Pass.screenRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavigationItem.Pass.screenRoute) {
                PassListScreen(
                    navController = mainNavController,
                    viewModel = passListViewModel,
                    onPassClick = { carId ->
                        rootNavController.navigate("${Const.CAR}/$carId")
                    }
                )
            }
            composable(NavigationItem.Car.screenRoute) {
                CarListScreen(
                    navController = mainNavController
                )
            }
            composable(NavigationItem.Setting.screenRoute) {
                SettingScreen(
                    navController = mainNavController
                )
            }
        }
    }

    when {
        openDialog.value -> {
            DialogScreen(
                context = LocalContext.current,
                onDismissRequest = { openDialog.value = false },
                onConfirmation = { plateNumber ->
                    openDialog.value = false
                    passListViewModel.addPass(
                        Pass(
                            id = UUID.randomUUID().toString(),
                            timestampMillis = System.currentTimeMillis(),
                            direction = Direction.IN,
                            plateNumber = plateNumber,
                            source = Source.MANUAL
                        )
                    )
                }
            )
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
    Column(
        modifier = Modifier.shadow(elevation = 8.dp)
    ) {
        HorizontalDivider(
            thickness = 0.5.dp
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
}

sealed class NavigationItem(
    val title: Int,
    val icon: Int,
    val screenRoute: String
) {
    data object Pass : NavigationItem(
        title = R.string.tab_pass,
        icon = R.drawable.icon_pass,
        screenRoute = Const.PASS
    )

    data object Car : NavigationItem(
        title = R.string.tab_car,
        icon = R.drawable.icon_car,
        screenRoute = Const.CAR
    )

    data object Setting :
        NavigationItem(
            title = R.string.tab_setting,
            icon = R.drawable.icon_setting,
            screenRoute = Const.SETTING
        )
}