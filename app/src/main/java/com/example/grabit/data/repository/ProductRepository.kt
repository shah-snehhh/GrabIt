package com.example.grabit.data.repository

import com.example.grabit.data.model.Product
import com.example.grabit.data.retrofit.ProductApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productApi: ProductApi
) {
    suspend fun getProductList() : List<Product> {
        return productApi.getProducts()
    }

    suspend fun getTopProductList(limit: Int = 10): List<Product> {
        return productApi.getTopProducts(limit)
    }

    suspend fun getProductDetail(productId :Int): Product {
        return productApi.getProductById(productId)
    }
}
