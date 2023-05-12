package com.root14.cryptocurrencytracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.cryptocurrencytracker.database.entity.Coin
import com.root14.cryptocurrencytracker.database.repo.DbRepo
import com.root14.cryptocurrencytracker.network.Resource
import com.root14.cryptocurrencytracker.network.Status
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ilkay on 11,May, 2023
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    val dbRepo: DbRepo
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
    suspend fun getCoinById(coinId: String): MutableLiveData<Resource<CoinById>> {
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

    suspend fun toggleCoinFavorite(coinId: String) {
        dbRepo.toggleCoinFavorite(coinId)
    }

    /*-----------------------*/
    suspend fun getFavorite(coinId: String) = dbRepo.getFavorite(coinId)

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