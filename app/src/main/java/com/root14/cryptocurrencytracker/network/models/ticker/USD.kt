package com.root14.cryptocurrencytracker.network.models.ticker

import com.google.gson.annotations.SerializedName

data class USD(
    @SerializedName("price") var price: String? = null,
    @SerializedName("percent_change_24h") var percentChange24h: String? = null,
)