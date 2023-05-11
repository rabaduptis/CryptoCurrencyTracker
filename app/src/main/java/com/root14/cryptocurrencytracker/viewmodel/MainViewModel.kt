package com.root14.cryptocurrencytracker.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.root14.cryptocurrencytracker.network.Resource
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.repo.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    suspend fun getAllCoin(): MutableLiveData<Resource<List<AllCoins>>> {
        val getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
        getAllCoins.postValue(Resource.loading(null))

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                getAllCoins.postValue(Resource.success(it.body()))
            } else {
                getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
        return getAllCoins
    }

    suspend fun getCoinById(coinId: String): MutableLiveData<Resource<CoinById>> {
        val getCoinById = MutableLiveData<Resource<CoinById>>()
        getAllCoin().postValue(Resource.loading(null))

        mainRepository.getCoinById(coinId).let {
            if (it.isSuccessful) {
                getCoinById.postValue(Resource.success(it.body()))
            } else {
                getCoinById.postValue(Resource.error(it.errorBody().toString(), null))
            }
            return getCoinById
        }
    }

    fun tickerByCoin() {

    }


}