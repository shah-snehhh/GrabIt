package com.example.grabit.ui.flow.product.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.repository.ProductRepository
import com.example.grabit.ui.navigation.AppDestinations.ProductDetail.KEY_PRODUCT_ID
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val navigator: AppNavigator,
    private val productRepository: ProductRepository
) : ViewModel() {
    private var productId: Int = savedStateHandle.get<String>(KEY_PRODUCT_ID)?.toInt()
        ?: throw IllegalArgumentException("Product id is required")

    private val _state = MutableStateFlow(ProductDetailState())
    val state = _state.asStateFlow()


    init {
        _state.value = _state.value.copy(productId = productId)
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


    private val _cartList = MutableStateFlow<List<Product>>(emptyList())
    val cartList: StateFlow<List<Product>> = _cartList.asStateFlow()

    fun addToCart(product: Product) {
        viewModelScope.launch {
            val currentList = _cartList.value.toMutableList()
            val index = currentList.indexOfFirst { it.id == product.id }
            if (index >= 0) {
                val existingProduct = currentList[index]
                currentList[index] = existingProduct.copy(quantity = existingProduct.quantity + 1)
            } else {
                currentList.add(product)
            }
            _cartList.value = currentList
        }
    }

    fun popBackStack() {
        navigator.navigateBack()
    }
}

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val productId: Int? = null
)