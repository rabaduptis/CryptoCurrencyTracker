package com.root14.cryptocurrencytracker.network.models.response.ticker

import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("price") var price: Double? = null,
    @SerializedName("percent_change_24h") var change24H: Double? = null
)