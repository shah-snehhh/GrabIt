package com.example.grabit.ui.flow.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.repository.ProductRepository
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    fun popBack() {
        navigator.navigateBack()
    }

    fun loadProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val products = productRepository.getProductList()
                allProducts = products
                _state.value = _state.value.copy(
                    productList = products,
                    isLoading = false,
                    error = ""
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Something went wrong"
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _state.value = _state.value.copy(query = query)

        if (query.length < 3) {
            _state.value = _state.value.copy(productList = allProducts)
        } else {
            val filtered = allProducts.filter {
                it.title.contains(query, ignoreCase = true) ||
                        it.description.contains(query, ignoreCase = true)
            }
            _state.value = _state.value.copy(productList = filtered)
        }
    }

    fun navigateToProductDetail(productId: Int) {
        navigator.navigateTo(
            AppDestinations.ProductDetail.setArgs(
                productId
            ).path
        )
    }
}

data class SearchScreenState(
    var productList: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val appending: Boolean = false,
    var query: String = "",
    val hasMoreProducts: Boolean = true,
    val isLoadingMore: Boolean = false,
    var error: String = ""
)
