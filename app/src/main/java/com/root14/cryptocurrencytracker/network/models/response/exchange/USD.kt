package com.root14.cryptocurrencytracker.network.models.response.exchange

import com.google.gson.annotations.SerializedName

data class USD (

  @SerializedName("price"      ) var price     : Double? = null,
  @SerializedName("volume_24h" ) var volume24h : Double? = null

)