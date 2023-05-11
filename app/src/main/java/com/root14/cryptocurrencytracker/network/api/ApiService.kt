package com.root14.cryptocurrencytracker.network.api

import retrofit2.Response
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(): Response<Login>

    @POST("register")
    suspend fun register(): Response<Register>

    suspend fun loadFeed(): Response<Feed>
}