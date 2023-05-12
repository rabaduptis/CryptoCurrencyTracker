package com.root14.cryptocurrencytracker.network.models.response
import com.google.gson.annotations.SerializedName


data class Exchange (

  @SerializedName("exchange_id"               ) var exchangeId             : String?  = null,
  @SerializedName("exchange_name"             ) var exchangeName           : String?  = null,
  @SerializedName("pair"                      ) var pair                   : String?  = null,
  @SerializedName("base_currency_id"          ) var baseCurrencyId         : String?  = null,
  @SerializedName("base_currency_name"        ) var baseCurrencyName       : String?  = null,
  @SerializedName("quote_currency_id"         ) var quoteCurrencyId        : String?  = null,
  @SerializedName("quote_currency_name"       ) var quoteCurrencyName      : String?  = null,
  @SerializedName("market_url"                ) var marketUrl              : String?  = null,
  @SerializedName("category"                  ) var category               : String?  = null,
  @SerializedName("fee_type"                  ) var feeType                : String?  = null,
  @SerializedName("outlier"                   ) var outlier                : Boolean? = null,
  @SerializedName("adjusted_volume_24h_share" ) var adjustedVolume24hShare : Double?  = null,
  @SerializedName("quotes"                    ) var quotes                 : Quotes?  = Quotes(),
  @SerializedName("trust_score"               ) var trustScore             : String?  = null,
  @SerializedName("last_updated"              ) var lastUpdated            : String?  = null

)