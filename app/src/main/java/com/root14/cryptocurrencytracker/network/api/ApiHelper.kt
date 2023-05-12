package com.root14.cryptocurrencytracker.network.api

import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.exchange.Exchange
import retrofit2.Response

interface ApiHelper {
    suspend fun listAllCoins(): Response<List<AllCoins>>
    suspend fun getCoinById(coinID: String): Response<CoinById>
    suspend fun getExchangeRate(coinID: String): Response<Exchange>
}