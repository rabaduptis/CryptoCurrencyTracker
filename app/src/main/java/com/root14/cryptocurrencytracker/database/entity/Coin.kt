package com.root14.cryptocurrencytracker.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.root14.cryptocurrencytracker.network.models.response.CoinById

/**
 * Created by ilkay on 11,May, 2023
 */

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "primaryKey") var coinById: Int = 0,
    @ColumnInfo(name = "id") var id: String? = null,
    @ColumnInfo(name = "name") var name: String? = null,
    @ColumnInfo(name = "symbol") var symbol: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "hashAlgorithm") var hashAlgorithm: String? = null,
    @ColumnInfo(name = "logoURL") var logoURL: String? = null,
    @ColumnInfo(name = "price") var price: String? = null,
    @ColumnInfo(name = "percentChange24h") var percentChange24h: String? = null
)
