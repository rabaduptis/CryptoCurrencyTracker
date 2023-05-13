package com.root14.cryptocurrencytracker.viewmodel

import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.root14.cryptocurrencytracker.data.entity.Coin
import com.root14.cryptocurrencytracker.data.repo.DbRepo
import com.root14.cryptocurrencytracker.network.Resource
import com.root14.cryptocurrencytracker.network.Status
import com.root14.cryptocurrencytracker.network.models.response.AllCoins
import com.root14.cryptocurrencytracker.network.models.response.CoinById
import com.root14.cryptocurrencytracker.network.models.response.TickerById
import com.root14.cryptocurrencytracker.network.repo.AuthRepository
import com.root14.cryptocurrencytracker.network.repo.MainRepository
import com.root14.cryptocurrencytracker.network.state.SignInState
import com.root14.cryptocurrencytracker.network.state.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by ilkay on 11,May, 2023
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dbRepo: DbRepo,
    private val glide: RequestManager,
    private val sharedPreferences: SharedPreferences,
    private val repository: AuthRepository,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    val _signInState = Channel<SignInState>()
    val signInState = _signInState.receiveAsFlow()
    suspend fun login(email: String, password: String) = viewModelScope.launch {
        repository.loginUser(email, password).collect {

            when (it.status) {
                Status.LOADING -> {
                    _signInState.send(SignInState(isLoading = true))
                }

                Status.ERROR -> {
                    _signInState.send(SignInState(isError = "Sign In Error"))
                }

                Status.SUCCESS -> {
                    _signInState.send(SignInState(isSuccess = "Sign In Success "))
                }
            }
        }
    }

    val _signUpState = Channel<SignUpState>()
    val signUpState = _signUpState.receiveAsFlow()
    suspend fun register(email: String, password: String) {
        repository.registerUser(email, password).collect {
            when (it.status) {
                Status.LOADING -> {
                    _signUpState.send(SignUpState(isLoading = true))
                }

                Status.ERROR -> {
                    _signUpState.send(SignUpState(isError = "Sign In Error"))
                }

                Status.SUCCESS -> {
                    _signUpState.send(SignUpState(isSuccess = "Sign In Success "))
                }
            }
        }
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
    var resultFavCoins = MutableLiveData<List<Coin>>()
    var isLoadingFavCoins by mutableStateOf(true)
    suspend fun getFavoriteCoins() {
        resultFavCoins.postValue(dbRepo.getFavoriteCoins())
        isLoadingFavCoins = false
    }/*-----------------------*/


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
                resource: Drawable, transition: Transition<in Drawable?>?
            ) {
                imageState.value = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {}
        })
    }/*-----------------------*/

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

    private val _backGroundWorker = MutableLiveData<Double>()
    var backGroundWorker: LiveData<Double> = _backGroundWorker
    suspend fun backGroundWork() {
        val backGroundWorker = sharedPreferences.getString("backGroundWorker", "")
        getTickerById(backGroundWorker!!).observeForever {
            when (it.status) {
                Status.LOADING -> {
                    println("fetch selected coin on background")
                }

                Status.ERROR -> {
                    "cannot fetch coin on background ${it.message}"
                }

                Status.SUCCESS -> {
                    _backGroundWorker.postValue(it.data?.quotes?.USD?.price)
                }
            }
        }
    }


    fun addFirebaseFavorite(coinId: String, fav: Boolean) {
        val databaseRef = firebaseDatabase.getReference("favoriteCoins")

        val _coin = Coin(id = coinId, favorite = fav)

        databaseRef.child(coinId).setValue(_coin)
    }


    private val _firebaseFavCoins = MutableLiveData<MutableList<Coin>>()
    val firebaseFavCoins: LiveData<MutableList<Coin>> = _firebaseFavCoins
    fun getFirebaseCoins() {
        val databaseRef = firebaseDatabase.getReference("favoriteCoins")
        println("error happened -1")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                println("error happened 0")
                val coins = mutableListOf<Coin>()
                for (coinSnapshot in dataSnapshot.children) {
                    val coin = coinSnapshot.getValue(Coin::class.java)
                    coin?.let {
                        coins.add(it)
                    }
                }
                _firebaseFavCoins.postValue(coins)
                println("error happened 1")
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error
                println("error happened ${error.message}")
            }
        })
    }

    init {
        viewModelScope.launch {
            getAllCoins()
            withContext(Dispatchers.IO) {
                dbRepo.getFavoriteCoins()
            }
        }
    }

    object MyViewModelSingleton {
        private lateinit var instance: MainViewModel

    }
}
