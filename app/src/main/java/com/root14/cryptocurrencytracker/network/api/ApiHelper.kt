package com.root14.cryptocurrencytracker.network.api

import retrofit2.Response

interface ApiHelper {
    suspend fun login(): Response<Login>
    suspend fun register(): Response<Register>
    suspend fun loadFeed(): Response<Feed>
}