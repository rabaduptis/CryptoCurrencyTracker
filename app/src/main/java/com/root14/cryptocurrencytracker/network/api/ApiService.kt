package com.root14.cryptocurrencytracker.network.api

import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.TickerById
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("coins")
    suspend fun listAllCoins(): Response<List<AllCoins>>

    @GET("coins/{coinId}")
    suspend fun getCoinById(@Path("coinId") coinId: String): Response<CoinById>

    @GET("tickers/{coinId}")
    suspend fun getTickerById(@Path("coinId") coinId: String): Response<TickerById>
}