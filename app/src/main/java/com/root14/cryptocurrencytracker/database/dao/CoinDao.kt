package com.root14.cryptocurrencytracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.root14.cryptocurrencytracker.database.entity.Coin

/**
 * Created by ilkay on 11,May, 2023
 */

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: Coin)

    @Query("SELECT * FROM coins")
    suspend fun getCoin(): List<Coin>

}