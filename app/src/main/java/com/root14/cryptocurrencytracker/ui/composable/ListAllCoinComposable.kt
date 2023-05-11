package com.root14.cryptocurrencytracker.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Created by ilkay on 11,May, 2023
 */


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListAllCoinComposable() {

    val dummyCoinList = listOf<String>("btc", "eth", "otherCoin")

    Surface(color = Color.Black) {
        LazyColumn() {
            items(dummyCoinList) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start,
                        color = Color.White
                    )
                    Icon(
                        //turn to Icons.Filled.Favorite when added fav
                        imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Add favorite $item",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
        }
    }
}