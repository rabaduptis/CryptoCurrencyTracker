package com.root14.cryptocurrencytracker.viewmodel

import android.content.SharedPreferences
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
import androidx.lifecycle.viewmodel.compose.viewModel
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
    private val glide: RequestManager,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {
    private val loginStatus = MutableLiveData<Boolean>()
    private val signInStatus = MutableLiveData<Boolean>()

    var isLoadingFav by mutableStateOf(true)

    suspend fun login() {
    }

    fun signIn() {

    }

    /*-----------------------*/

    fun setInitialized(key: String = "init", value: Boolean = true) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    /**
     * @return true if first init
     */
    fun checkFirstInit(key: String = "init") = sharedPreferences.getBoolean(key, true)

    /*-----------------------*/

    private val _getAllCoins = MutableLiveData<Resource<List<AllCoins>>>()
    val getAllCoins: LiveData<Resource<List<AllCoins>>>
        get() = _getAllCoins

    /**
     * coinf from API
     */
    private fun getAllCoin() = viewModelScope.launch {
        _getAllCoins.postValue(Resource.loading(null))
        isLoading = true

        mainRepository.listAllCoins().let {
            if (it.isSuccessful) {
                _getAllCoins.postValue(Resource.success(it.body()))
                isLoading = false
            } else {
                _getAllCoins.postValue(Resource.error(it.errorBody().toString(), null))
                isLoading = false
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
            isLoading = false
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
    /*-----------------------*/


    //Temiz
    var isLoading by mutableStateOf(true)
    var loadingProgress by mutableStateOf(0f)
    var result = MutableLiveData<List<Coin>>()

    suspend fun getAllCoins() {

        val coinsFromDb = dbRepo.getCoins()
        if (coinsFromDb.isEmpty()) {
            //get from api
            getAllCoin()
            val coinsFromApi = getAllCoins
            coinsFromApi.observeForever {
                when (it.status) {
                    Status.SUCCESS -> {
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                var _result = mutableListOf<Coin>()
                                it.data?.forEachIndexed { index, allCoins ->
                                    //save to db
                                    dbRepo.insertCoin(Coin(id = allCoins.id, name = allCoins.name))
                                    //for list
                                    _result.add(
                                        index = index,
                                        element = Coin(id = allCoins.id, name = allCoins.name)
                                    )
                                    loadingProgress = (((index + 1) * 100) / it.data.size).toFloat()
                                }
                                result.postValue(_result)
                                isLoading = false
                            }
                        }
                    }

                    Status.LOADING -> {
                        isLoading = true
                    }

                    Status.ERROR -> {
                        isLoading = false
                    }
                }
            }
        } else {
            result.postValue(coinsFromDb)
            isLoading = false
        }
    }

    init {
        viewModelScope.launch {
            getAllCoins()
        }
    }
}
