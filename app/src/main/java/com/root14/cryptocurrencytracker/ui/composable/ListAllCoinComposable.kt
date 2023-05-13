package com.root14.cryptocurrencytracker.ui.composable

import android.widget.ProgressBar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.root14.cryptocurrencytracker.database.entity.Coin
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by ilkay on 11,May, 2023
 */


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListAllCoinComposable(
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    var isLoading by remember {
        mutableStateOf(true)
    }

    var coinList by remember {
        mutableStateOf(emptyList<Coin>())
    }
    val lifecycleOwner = LocalLifecycleOwner.current
    mainViewModel.result.observe(lifecycleOwner) {
        coinList = it
        isLoading = mainViewModel.isLoading
    }

    Surface(color = Color.Black) {
        if (isLoading) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(top = 24.dp, bottom = 24.dp))
                Text(
                    text = "Loading...",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                if (mainViewModel.checkFirstInit()) {
                    LinearProgressIndicator(
                        progress = (mainViewModel.loadingProgress / 100F),
                        trackColor = Color.White, modifier = Modifier.padding(top = 12.dp)
                    )

                    Text(
                        text = "Creating database, this may take some time.",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 12.sp
                    )
                }
            }
        } else {
            val favoriteStatuses = remember { MutableList(coinList.size) { false } }
            LazyColumn() {
                itemsIndexed(coinList) { index, item ->

                    var isFavorite = favoriteStatuses[index]
                    var isAddingFavorite by remember { mutableStateOf(false) }
                    mainViewModel.setInitialized()

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
                                if (!isAddingFavorite) {
                                    isAddingFavorite = true
                                    mainViewModel.viewModelScope.launch {
                                        mainViewModel.toggleCoinFavorite(item.id!!)
                                    }
                                    isFavorite = !isFavorite
                                    println("toggle ettim ${item.id!!}")
                                    isAddingFavorite = false
                                }
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