package com.root14.cryptocurrencytracker.network.repo

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.root14.cryptocurrencytracker.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val firebaseAuth: FirebaseAuth) :
    AuthRepository {
    override fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.success(result))
        }.catch {
            emit(Resource.error(it.message.toString(), null))
        }
    }

    override fun registerUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.success(result))
        }.catch {
            emit(Resource.error(it.message.toString(), null))
        }
    }
}