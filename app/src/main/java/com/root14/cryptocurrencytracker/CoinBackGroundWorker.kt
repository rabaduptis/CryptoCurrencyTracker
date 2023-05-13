package com.root14.cryptocurrencytracker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.root14.cryptocurrencytracker.network.Status
import com.root14.cryptocurrencytracker.viewmodel.MainViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject


class CoinBackGroundWorker(
    context: Context,
    params: WorkerParameters

) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        //some stuff
        //mainViewModel.backGroundWork()
        return Result.success()
    }

}