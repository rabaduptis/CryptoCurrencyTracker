package com.root14.cryptocurrencytracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.root14.cryptocurrencytracker.database.dao.CoinDao
import com.root14.cryptocurrencytracker.database.entity.Coin

/**
 * Created by ilkay on 11,May, 2023
 */


@Database(entities = [Coin::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao
}