package com.example.grabit.ui.flow.dashboard.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.repository.CartRepository
import com.example.grabit.data.storage.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val cartRepository: CartRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(CartScreenState())
    val state = _state.asStateFlow()

    init {
        _state.value = state.value.copy(currentUser = userPreferences.currentUser)
    }

    fun loadUserCart() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true)
            try {
                val products = cartRepository.getUserCartWithProducts(
                    state.value.currentUser?.id ?: 0
                )
                updateCartState(products, isLoading = false)
            } catch (e: Exception) {
                updateCartState(emptyList(), isLoading = false)
            }
        }
    }

    fun removeFromCart(product: Product) {
        val currentList = _state.value.cartList.toMutableList()
        currentList.removeIf { it.id == product.id }
        updateCartState(currentList)
    }

    fun incrementQuantity(product: Product) {
        val currentList = _state.value.cartList.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index >= 0) {
            val existingProduct = currentList[index]
            currentList[index] = existingProduct.copy(quantity = existingProduct.quantity + 1)
        }
        updateCartState(currentList)
    }

    fun decrementQuantity(product: Product) {
        val currentList = _state.value.cartList.toMutableList()
        val index = currentList.indexOfFirst { it.id == product.id }
        if (index >= 0) {
            val existingProduct = currentList[index]
            if (existingProduct.quantity > 1) {
                currentList[index] = existingProduct.copy(quantity = existingProduct.quantity - 1)
            }
        }
        updateCartState(currentList)
    }

    private fun updateCartState(products: List<Product>, isLoading: Boolean = false) {
        val totalCount = products.sumOf { it.quantity }
        val totalPrice = products.sumOf { it.price * it.quantity }

        _state.value = _state.value.copy(
            cartList = products,
            count = totalCount,
            totalPrice = totalPrice,
            isLoading = isLoading
        )
    }
}

data class CartScreenState(
    val currentUser: UserResponse? = null,
    val isLoading: Boolean = false,
    val cartList: List<Product> = emptyList(),
    val count: Int = 0,
    val totalPrice: Double = 0.0
)