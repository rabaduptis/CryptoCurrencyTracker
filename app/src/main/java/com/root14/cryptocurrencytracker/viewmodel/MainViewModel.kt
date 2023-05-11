package com.root14.cryptocurrencytracker.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.root14.cryptocurrencytracker.network.Resource
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
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val loginStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Boolean>()

    private val _getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
    val getAllCoins: LiveData<Resource<List<AllCoins>>>
        get() = _getAllCoins


    private val _getCoinById = MutableLiveData<Resource<CoinById>>()

    val getCoinById: LiveData<Resource<CoinById>>
        get() = _getCoinById

    init {
        getAllCoin()
    }

    suspend fun login() {

    }

    fun signIn() {

    }

    fun getAllCoin() = viewModelScope.launch {
        _getAllCoins.postValue(Resource.loading(null))

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                _getAllCoins.postValue(Resource.success(it.body()))
            } else {
                _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }

    fun getCoinById(coinId: String) = viewModelScope.launch {
        _getCoinById.postValue(Resource.loading(null))

        mainRepository.getCoinById(coinId).let {
            if (it.isSuccessful) {
                _getCoinById.postValue(Resource.success(it.body()))
            } else {
                _getCoinById.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
    }


    fun tickerByCoin() {

    }


}