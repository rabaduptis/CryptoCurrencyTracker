package com.root14.cryptocurrencytracker.network.models

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("rank") var rank: Int? = null,
    @SerializedName("is_new") var isNew: Boolean? = null,
    @SerializedName("is_active") var isActive: Boolean? = null,
    @SerializedName("type") var type: String? = null
)

