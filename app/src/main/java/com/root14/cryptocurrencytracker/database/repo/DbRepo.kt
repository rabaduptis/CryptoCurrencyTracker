package com.root14.cryptocurrencytracker.database.repo

import com.root14.cryptocurrencytracker.database.dao.CoinDao
import com.root14.cryptocurrencytracker.database.entity.Coin
import javax.inject.Inject

/**
 * Created by ilkay on 12,May, 2023
 */
class DbRepo @Inject constructor(private val coinDao: CoinDao) {
    suspend fun getCoins() = coinDao.getCoin()
    suspend fun insertCoin(coin: Coin) = coinDao.insertCoin(coin)
}