package com.root14.cryptocurrencytracker.viewmodel

import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.root14.cryptocurrencytracker.database.entity.Coin
import com.root14.cryptocurrencytracker.database.repo.DbRepo
import com.root14.cryptocurrencytracker.network.Resource
import com.root14.cryptocurrencytracker.network.Status
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.TickerById
import com.root14.cryptocurrencytracker.network.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by ilkay on 11,May, 2023
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    val dbRepo: DbRepo,
    private val glide: RequestManager
) : ViewModel() {
    private val loginStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Boolean>()

    suspend fun login() {
    }

    fun signIn() {

    }
    /*-----------------------*/

    private val _getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
    val getAllCoins: LiveData<Resource<List<AllCoins>>>
        get() = _getAllCoins

    private fun getAllCoin() = viewModelScope.launch {
        _getAllCoins.postValue(Resource.loading(null))

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                _getAllCoins.postValue(Resource.success(it.body()))
            } else {
                _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    /*-----------------------*/
    suspend fun getCoinById(coinId: String): LiveData<Resource<CoinById>> {
        val _getCoinById = MutableLiveData<Resource<CoinById>>()
        _getCoinById.postValue(Resource.loading(null))
        mainRepository.getCoinById(coinId).let {
            if (it.isSuccessful) {
                _getCoinById.postValue(Resource.success(it.body()))
            } else {
                _getCoinById.postValue(Resource.error(it.errorBody().toString(), null))
            }
            return _getCoinById
        }
    }

    /*-----------------------*/
    suspend fun getTickerById(coinId: String): LiveData<Resource<TickerById>> {
        val _getExchangeRate = MutableLiveData<Resource<TickerById>>()
        _getExchangeRate.postValue(Resource.loading(null))
        mainRepository.getTickerById(coinId).let {
            if (it.isSuccessful) {
                _getExchangeRate.postValue(Resource.success(it.body()))
            } else {
                _getExchangeRate.postValue(Resource.error(it.errorBody().toString(), null))
            }
            return _getExchangeRate
        }
    }

    /*-----------------------*/
    suspend fun toggleCoinFavorite(coinId: String) {
        dbRepo.toggleCoinFavorite(coinId)
    }

    /*-----------------------*/
    suspend fun getFavorite(coinId: String) = dbRepo.getFavorite(coinId)

    /*-----------------------*/
    suspend fun getFavoriteCoins(): List<Coin> = withContext(Dispatchers.IO) {
        dbRepo.getFavoriteCoins()
    }

    /*-----------------------*/
    fun loadImage(imageUrl: String, imageState: MutableState<Drawable?>) {
        glide.load(imageUrl).into(object : CustomTarget<Drawable?>() {
            override fun onResourceReady(
                resource: Drawable,
                transition: Transition<in Drawable?>?
            ) {
                imageState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }

    /*-----------------------*/

    init {
        //eğer dbde değer yoksa getAllCoin yap bitene kadar ekranda loading göster
        getAllCoin()
        //getAllTicker()
        getAllCoins.observeForever {
            it.data?.forEachIndexed { index, allCoins ->

                when (it.status) {
                    Status.SUCCESS -> {
                        println("status sucess ${it.status}")

                        viewModelScope.launch {
                            dbRepo.insertCoin(
                                Coin(
                                    id = allCoins.id,
                                    name = allCoins.name,
                                    symbol = allCoins.symbol
                                )
                            )
                        }
                    }

                    Status.LOADING -> {
                        "status load ${it.status}"
                    }

                    Status.ERROR -> {
                        "status error ${it.status}"
                    }
                }
            }
        }
    }
}