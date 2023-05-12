package com.root14.cryptocurrencytracker.network.api

import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.TickerById
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun listAllCoins(): Response<List<AllCoins>> = apiService.listAllCoins()
    override suspend fun getCoinById(coinID: String): Response<CoinById> =
        apiService.getCoinById(coinID)

    override suspend fun getTickerById(coinID: String): Response<TickerById> =
        apiService.getTickerById(coinID)
}