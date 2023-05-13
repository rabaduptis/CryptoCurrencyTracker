package com.root14.cryptocurrencytracker.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.root14.cryptocurrencytracker.data.entity.Coin

/**
 * Created by ilkay on 11,May, 2023
 */

@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coin: Coin)

    @Query("SELECT * FROM coins")
    suspend fun getCoin(): List<Coin>


    @Query("UPDATE coins SET favorite = NOT favorite WHERE id = :coinId")
    suspend fun toggleCoinFavorite(coinId: String)

    @Query("SELECT favorite FROM coins WHERE id = :coinId")
    suspend fun getFavorite(coinId: String): Boolean?


    @Query("SELECT * FROM coins WHERE favorite = 1")
    fun getFavoriteCoins(): List<Coin>

    @Query("SELECT EXISTS (SELECT 1 FROM coins LIMIT 1)")
    fun hasAnyCoin(): Boolean

}