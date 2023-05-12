package com.root14.cryptocurrencytracker.network.models.response

import com.google.gson.annotations.SerializedName
import com.root14.cryptocurrencytracker.network.models.response.ticker.Quotes


data class TickerById(

    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("quotes") var quotes: Quotes? = Quotes()

)