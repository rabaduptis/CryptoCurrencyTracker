package com.root14.cryptocurrencytracker.network.repo

import com.root14.cryptocurrencytracker.network.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun listAllCoins() = apiHelper.listAllCoins()
    suspend fun getCoinById(coinID: String) = apiHelper.getCoinById(coinID)
    suspend fun getExchangeRate(coinID: String) = apiHelper.getExchangeRate(coinID)
}