package com.root14.cryptocurrencytracker.network.repo

import com.root14.cryptocurrencytracker.network.api.ApiHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun login() = apiHelper.login()
    suspend fun register() = apiHelper.register()
    suspend fun loadFeed()= apiHelper.loadFeed()
}