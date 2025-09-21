package com.example.grabit.data.model

data class CartResponse(
    val id: Int,
    val userId: Int,
    val date: String,
    val products: List<CartProduct>,
    val __v: Int
)

data class CartProduct(
    val productId: Int,
    val quantity: Int
)