package com.example.grabit.data.retrofit

import com.example.grabit.data.config.AppConfig.GET_USER_CART
import com.example.grabit.data.model.CartResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiCart {

    @GET(GET_USER_CART)
    suspend fun getUserCarts(
        @Path("userId") userId: Int,
    ): List<CartResponse>
}
