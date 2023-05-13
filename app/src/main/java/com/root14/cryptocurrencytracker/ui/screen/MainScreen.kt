package com.root14.cryptocurrencytracker.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.root14.cryptocurrencytracker.ui.composable.CoinDetailComposable
import com.root14.cryptocurrencytracker.ui.composable.FavoritesComposable
import com.root14.cryptocurrencytracker.ui.composable.ListAllCoinComposable
import com.root14.cryptocurrencytracker.ui.composable.LoginComposable
import com.root14.cryptocurrencytracker.ui.composable.SignInComposable
import com.root14.cryptocurrencytracker.ui.theme.DarkBlack
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by ilkay on 12,May, 2023
 */


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {

            BottomNavigation {
                BottomNavigationItem(
                    icon = { Icon(Icons.Filled.Build, contentDescription = "All coins") },
                    label = { Text("All coins") },
                    selected = navController.currentDestination?.route == "listAllCoin_destination",
                    onClick = {
                        navController.navigate("listAllCoin_destination") {
                            launchSingleTop = true
                        }
                    }
                )
                BottomNavigationItem(

                    icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
                    label = { Text("Favorites") },
                    selected = navController.currentDestination?.route == "favorites_destination",
                    onClick = {
                        navController.navigate("favorites_destination") {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) {
        Surface(color = DarkBlack, modifier = Modifier.fillMaxSize()) {
            MainScreenContent(navController = navController)
        }
    }
}

@Composable
fun MainScreenContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login_destination"
    ) {
        composable("listAllCoin_destination") {
            ListAllCoinComposable(navController = navController)
        }
        composable("favorites_destination") {
            FavoritesComposable(navController = navController)
        }

        composable(route = "login_destination") {
            LoginComposable(navController = navController)
        }
        composable("signUp_destination") {
            SignInComposable(navController = navController)
        }

        composable(
            "coinDetail/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId")
            coinId?.let {
                CoinDetailComposable(coinId = it)
            }
        }
    }
}