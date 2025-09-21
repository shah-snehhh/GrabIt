package com.example.grabit.data.retrofit

import com.example.grabit.data.config.AppConfig.REGISTER_USER_ACCOUNT
import com.example.grabit.data.model.RegisterRequest
import com.example.grabit.data.model.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST(REGISTER_USER_ACCOUNT)
    suspend fun registerUser(
        @Body request: RegisterRequest,
    ): RegisterResponse
}

