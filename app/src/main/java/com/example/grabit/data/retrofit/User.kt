package com.example.grabit.data.retrofit

import com.example.grabit.data.config.AppConfig.GET_ALL_USERS
import com.example.grabit.data.model.UserResponse
import retrofit2.http.GET

interface UserApi {

    @GET(GET_ALL_USERS)
    suspend fun getUsers(): List<UserResponse>
}