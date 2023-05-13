package com.root14.cryptocurrencytracker

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.root14.cryptocurrencytracker.ui.screen.MainScreen
import com.root14.cryptocurrencytracker.ui.theme.CryptoCurrencyTrackerTheme
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            CryptoCurrencyTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    MainScreen(sharedPreferences = sharedPreferences)
                }
            }
        }
    }


    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<CoinBackGroundWorker>(1, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .build()

    override fun onPause() {
        super.onPause()
        WorkManager.getInstance(this).enqueue(workRequest)
    }

    override fun onResume() {
        super.onResume()
    }
}