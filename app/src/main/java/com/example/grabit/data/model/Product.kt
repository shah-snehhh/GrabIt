package com.example.grabit.data.model

data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    var quantity: Int = 1,
    @Transient var rating: Double = 4.0,
    var isFavorite: Boolean = false
)