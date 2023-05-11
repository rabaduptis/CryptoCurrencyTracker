package com.root14.cryptocurrencytracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.cryptocurrencytracker.network.Resource
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.Ticker
import com.root14.cryptocurrencytracker.network.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by ilkay on 11,May, 2023
 */

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
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

    /*-----------------------*/
    private val _getAllTicker = MutableLiveData<Resource<List<Ticker>>>()
    val getAllTicker: LiveData<Resource<List<Ticker>>>
        get() = _getAllTicker

    private fun getAllTicker() = viewModelScope.launch {
        _getAllTicker.postValue(Resource.loading(null))

        mainRepository.getAllTicker().let {
            if (it.isSuccessful) {
                _getAllTicker.postValue(Resource.success(it.body()))
            } else {
                _getAllTicker.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    /*-----------------------*/
    suspend fun getTickerByCoinId(coinId: String): MutableLiveData<Resource<Ticker>> {
        val _getTickerByCoinId = MutableLiveData<Resource<Ticker>>()
        _getTickerByCoinId.postValue(Resource.loading(null))
        mainRepository.getTickerById(coinId).let {
            if (it.isSuccessful) {
                _getTickerByCoinId.postValue(Resource.success(it.body()))
            } else {
                _getTickerByCoinId.postValue(Resource.error(it.errorBody().toString(), null))
            }
            return _getTickerByCoinId
        }
    }


    init {
        getAllCoin()
        getAllTicker()
    }
}