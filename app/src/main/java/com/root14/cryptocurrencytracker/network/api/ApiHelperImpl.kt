package com.root14.cryptocurrencytracker.network.api

import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
) : ApiHelper {
    override suspend fun login(): Response<Login> = apiService.login()
    override suspend fun register(): Response<Register> = apiService.register()
    override suspend fun loadFeed(): Response<Feed> = apiService.loadFeed()
}