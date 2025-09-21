package com.example.grabit.data.retrofit

import com.example.grabit.data.config.AppConfig.GET_ALL_PRODUCTS
import com.example.grabit.data.config.AppConfig.GET_PRODUCT_DETAIL
import com.example.grabit.data.config.AppConfig.GET_TOP_PRODUCTS
import com.example.grabit.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET(GET_ALL_PRODUCTS)
    suspend fun getProducts(): List<Product>

    @GET(GET_TOP_PRODUCTS)
    suspend fun getTopProducts(@Query("limit") limit: Int): List<Product>

    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductById(@Path("id") id: Int): Product
}
