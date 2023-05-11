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
    //fun sign-in
    //fun login
    //fun listAllCoin
    //fun tickerByCoin-> list coin by id


    private val loginStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Boolean>()


    suspend fun login() {

    }

    fun signIn() {

    }

    //init de list all yap değişkeni observe et
    suspend fun getAllCoin(): MutableLiveData<Resource<List<AllCoins>>> {
        val _getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
        _getAllCoins.postValue(Resource.loading(null))

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                _getAllCoins.postValue(Resource.success(it.body()))
            } else {
                _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
            }
        }
        return _getAllCoins
    }

    fun tickerByCoin() {

    }


}