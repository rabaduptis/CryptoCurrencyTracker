package com.root14.cryptocurrencytracker.network.models.response.ticker

import com.google.gson.annotations.SerializedName

data class Quotes(
    @SerializedName("USD") var USD: USD? = USD()
)