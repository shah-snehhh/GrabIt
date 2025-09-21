package com.example.grabit.ui.flow.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.repository.CartRepository
import com.example.grabit.data.repository.ProductRepository
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations.ProductDetail.KEY_PRODUCT_ID
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: AppNavigator,
    private val productRepository: ProductRepository,
    private val cartRepository: CartRepository,
    userPreferences: UserPreferences
) : ViewModel() {
    private var productId: Int = savedStateHandle.get<String>(KEY_PRODUCT_ID)?.toInt()
        ?: throw IllegalArgumentException("Product id is required")

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()

    private var user: UserResponse? = null

    init {
        user = userPreferences.currentUser
        _state.value = _state.value.copy(productId = productId, currentUser = user)
        fetchProductDetail()
    }

    private fun fetchProductDetail() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response = productRepository.getProductDetail(productId)
                _state.value = _state.value.copy(product = response, isLoading = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun addToCart(product: Product) {
        viewModelScope.launch {
            try {
                val response = cartRepository.createCart(
                    user?.id ?: 1,
                    listOf(product)
                )
                println("Single item cart created: $response")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun popBackStack() {
        navigator.navigateBack()
    }
}

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val productId: Int? = null,
    val currentUser: UserResponse? = null
)