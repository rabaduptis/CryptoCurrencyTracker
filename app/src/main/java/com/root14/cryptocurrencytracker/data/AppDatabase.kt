package com.root14.cryptocurrencytracker.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.root14.cryptocurrencytracker.data.dao.CoinDao
import com.root14.cryptocurrencytracker.data.entity.Coin

/**
 * Created by ilkay on 11,May, 2023
 */


@Database(entities = [Coin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}