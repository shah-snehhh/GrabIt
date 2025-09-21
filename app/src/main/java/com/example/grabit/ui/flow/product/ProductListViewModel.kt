package com.example.grabit.ui.flow.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.repository.ProductRepository
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    private var _state = MutableStateFlow(ProductListScreenState())
    var state = _state.asStateFlow()

    init {
        loadProductList()
    }

    private fun loadProductList() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val productList = productRepository.getTopProductList()
                _state.value = _state.value.copy(productList = productList)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = _state.value.copy(error = e.message)
            } finally {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = ""
                )
            }
        }
    }

    fun showProductDetails(productId: Int) {
        navigator.navigateTo(
            AppDestinations.ProductDetail.setArgs(
                productId
            ).path
        )
    }

    fun onPopBack() {
        navigator.navigateBack()
    }
}

data class ProductListScreenState(
    var isLoading: Boolean = false,
    var productList: List<Product> = emptyList(),
    var error: String? = ""
)