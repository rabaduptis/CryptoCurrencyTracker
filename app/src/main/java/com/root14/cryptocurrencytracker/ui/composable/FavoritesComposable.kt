package com.root14.cryptocurrencytracker.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.root14.cryptocurrencytracker.database.entity.Coin
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel

/**
 * Created by ilkay on 12,May, 2023
 */

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun FavoritesComposable(mainViewModel: MainViewModel = hiltViewModel()) {
    var isLoading by remember { mutableStateOf(true) }
    var coinList by remember {
        mutableStateOf(emptyList<Coin>())
    }
    LaunchedEffect(Unit) {
        coinList = mainViewModel.getFavoriteCoins()
        isLoading = false
    }


    Surface(color = Color.Black) {
        //loading screen
        if (isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp))
                Text(
                    text = "Loading...",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        } else {
            //if there is no fav coin
            if (coinList.isEmpty()) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No favorite coins found",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn() {
                    items(coinList) { item ->

                        var isFavorite by remember { mutableStateOf(false) }

                        LaunchedEffect(isFavorite) {
                            mainViewModel.toggleCoinFavorite(item.id!!)
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = item.name.toString(),
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start,
                                color = Color.White
                            )

                            IconButton(
                                onClick = {
                                    isFavorite = !isFavorite
                                },
                            ) {

                                Icon(
                                    //turn to Icons.Filled.Favorite when added fav
                                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = "Add favorite $item",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(24.dp),
                                )
                            }

                        }
                    }
                }
            }
        }
    }

}