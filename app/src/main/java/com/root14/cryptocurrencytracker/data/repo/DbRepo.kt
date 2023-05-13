package com.root14.cryptocurrencytracker.data.repo

import com.root14.cryptocurrencytracker.data.dao.CoinDao
import com.root14.cryptocurrencytracker.data.entity.Coin
import javax.inject.Inject

/**
 * Created by ilkay on 12,May, 2023
 */
class DbRepo @Inject constructor(private val coinDao: CoinDao) {
    suspend fun getCoins() = coinDao.getCoin()
    suspend fun insertCoin(coin: Coin) = coinDao.insertCoin(coin)

    suspend fun toggleCoinFavorite(coinId: String) = coinDao.toggleCoinFavorite(coinId)
    suspend fun getFavorite(coinId: String) = coinDao.getFavorite(coinId)
    suspend fun getFavoriteCoins() = coinDao.getFavoriteCoins()
    suspend fun hasAnyCoin() = coinDao.hasAnyCoin()
}