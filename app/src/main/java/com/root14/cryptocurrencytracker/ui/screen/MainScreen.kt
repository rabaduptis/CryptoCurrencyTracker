package com.root14.cryptocurrencytracker.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.root14.cryptocurrencytracker.ui.composable.CoinDetailComposable
import com.root14.cryptocurrencytracker.ui.composable.FavoritesComposable
import com.root14.cryptocurrencytracker.ui.composable.ListAllCoinComposable
import com.root14.cryptocurrencytracker.ui.composable.LoginComposable
import com.root14.cryptocurrencytracker.ui.composable.SignInComposable
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
        MainScreenContent(navController = navController)
    }
}

@Composable
fun MainScreenContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "listAllCoin_destination"
    ) {
        composable("listAllCoin_destination") {
            ListAllCoinComposable()
        }
        composable("favorites_destination") {
            FavoritesComposable()
        }
        composable("coinDetail_destination") {
            CoinDetailComposable()
        }
        composable("login_destination") {
            LoginComposable()
        }
        composable("signIn_destination") {
            SignInComposable()
        }

    }
}