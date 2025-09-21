package com.example.grabit.data.repository

import com.example.grabit.data.retrofit.AuthApi
import com.example.grabit.data.model.RegisterRequest
import com.example.grabit.data.model.RegisterResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApi: AuthApi,
) {
    suspend fun registerUser(request: RegisterRequest): RegisterResponse {
        return authApi.registerUser(request)
    }
}
