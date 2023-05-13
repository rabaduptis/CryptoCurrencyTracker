package com.root14.cryptocurrencytracker.network.repo

import com.google.firebase.auth.AuthResult
import com.root14.cryptocurrencytracker.network.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by ilkay on 13,May, 2023
 */
interface AuthRepository {
    fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
    fun registerUser(email: String, password: String): Flow<Resource<AuthResult>>
}