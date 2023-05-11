package com.root14.cryptocurrencytracker.network.models.response

import com.google.gson.annotations.SerializedName
import com.root14.cryptocurrencytracker.network.models.ticker.Quotes


data class Ticker(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("quotes") var quotes: Quotes? = Quotes()
)