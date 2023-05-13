package com.root14.cryptocurrencytracker.network.models.response

import com.google.gson.annotations.SerializedName

data class AllCoins(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
)

