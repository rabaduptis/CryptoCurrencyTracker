package com.root14.cryptocurrencytracker

import android.app.Application
import androidx.room.Room
import com.root14.cryptocurrencytracker.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
}