package com.root14.cryptocurrencytracker.viewmodel

import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
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

    var isLoadingFromDb by mutableStateOf(true)
    var isLoadingFromAPI by mutableStateOf(true)
    var isLoading by mutableStateOf(true)
    var isLoadingFav by mutableStateOf(true)

    suspend fun login() {
    }

    fun signIn() {

    }
    /*-----------------------*/

    private val _getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
    val getAllCoins: LiveData<Resource<List<AllCoins>>>
        get() = _getAllCoins

    /**
     * coinf from API
     */
    private fun getAllCoin() = viewModelScope.launch {
        _getAllCoins.postValue(Resource.loading(null))
        isLoadingFromAPI = true

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                _getAllCoins.postValue(Resource.success(it.body()))
                isLoadingFromAPI = false
            } else {
                _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
                isLoadingFromAPI = false
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
        isLoadingFav = false
        dbRepo.getFavoriteCoins()
    }

    /**
     * @return coin list from db
     */
    var getCoins = MutableLiveData<List<Coin>>()

    suspend fun getCoins() {
        withContext(Dispatchers.IO) {
            getCoins.postValue(dbRepo.getCoins())
            isLoadingFromDb = false
        }
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


    fun getAllCoin0() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            if (!dbRepo.hasAnyCoin()) {
                _getAllCoins.postValue(Resource.loading(null))
                isLoadingFromAPI = true

                mainRepository.listAllCoins().let {
                    if (it.isSuccessful) {
                        _getAllCoins.postValue(Resource.success(it.body()))
                        isLoadingFromAPI = false

                        // insert each coin to database
                        it.body()?.forEach { coin ->
                            viewModelScope.launch {
                                dbRepo.insertCoin(
                                    Coin(
                                        id = coin.id,
                                        name = coin.name,
                                        symbol = coin.symbol
                                    )
                                )
                            }
                        }
                    } else {
                        _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
                        isLoading = false
                    }
                }
            }
            getCoins()
            isLoading = false
        }

        fun initList(lifecycleOwner: LifecycleOwner) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    if (dbRepo.hasAnyCoin()) {
                        getCoins()
                        //isLoadingFromAPI = false
                    } else {
                        getAllCoin()
                        //isLoadingFromDb = false
                    }

                    getAllCoins.observe(lifecycleOwner) {
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
                                    isLoading = false
                                }

                                Status.LOADING -> {
                                    "status load ${it.status}"
                                    isLoading = true
                                }

                                Status.ERROR -> {
                                    "status error ${it.status}"
                                    isLoading = false
                                }
                            }
                        }
                    }
                }
            }
        }

        /*-----------------------*/

    }
}