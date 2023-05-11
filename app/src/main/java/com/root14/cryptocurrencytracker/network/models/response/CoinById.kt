package com.root14.cryptocurrencytracker.network.models.response

import com.google.gson.annotations.SerializedName

/**
 * Created by ilkay on 11,May, 2023
 */
data class CoinById(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("symbol") var symbol: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("hash_algorithm") var hashAlgorithm: String? = null,
    @SerializedName("logo") var logoURL: String? = null
)
