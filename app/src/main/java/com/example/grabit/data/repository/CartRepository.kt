package com.example.grabit.data.repository

import com.example.grabit.data.model.Product
import com.example.grabit.data.retrofit.ApiCart
import com.example.grabit.data.retrofit.ProductApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepository @Inject constructor(
    private val cartApi: ApiCart,
    private val productApi: ProductApi,
) {

    suspend fun getUserCartWithProducts(userId: Int): List<Product> {
        val carts = cartApi.getUserCarts(userId)
        if (carts.isEmpty()) return emptyList()

        val allProducts = mutableListOf<Product>()

        for (cart in carts) {
            for (item in cart.products) {
                val productDetail = productApi.getProductById(item.productId)
                allProducts.add(
                    productDetail.copy(quantity = item.quantity)
                )
            }
        }

        return allProducts
    }

    suspend fun createCart(userId: Int, products: List<Product>): Any {
        return cartApi.createCart(
            mapOf(
                "userId" to userId,
                "date" to "2025-09-21",
                "products" to products.map { product ->
                    mapOf(
                        "productId" to product.id,
                        "quantity" to product.quantity
                    )
                }
            )
        )
    }
}
