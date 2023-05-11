package com.root14.cryptocurrencytracker.network.models.response

import com.google.gson.annotations.SerializedName
import com.root14.cryptocurrencytracker.network.models.ticker.Quotes


data class TickerById(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("circulating_supply") var circulatingSupply: Int? = null,
    @SerializedName("total_supply") var totalSupply: Int? = null,
    @SerializedName("max_supply") var maxSupply: Int? = null,
    @SerializedName("beta_value") var betaValue: Double? = null,
    @SerializedName("first_data_at") var firstDataAt: String? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null,
    @SerializedName("quotes") var quotes: Quotes? = Quotes()

)