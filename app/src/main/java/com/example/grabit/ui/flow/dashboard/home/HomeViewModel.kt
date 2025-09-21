package com.example.grabit.ui.flow.dashboard.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.repository.ProductRepository
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    userPreferences: UserPreferences,
    private val productRepository: ProductRepository,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    init {
        val currentUser = userPreferences.currentUser
        _state.value = _state.value.copy(currentUser = currentUser)
        getTopProductList()
    }

    fun getTopProductList() {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val productList = productRepository.getTopProductList()
                _state.value = _state.value.copy(productList = productList)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun navigateToProductList() {
        navigator.navigateTo(AppDestinations.productList.path)
    }

    fun showProductDetails(productId: Int) {
        navigator.navigateTo(
            AppDestinations.ProductDetail.setArgs(
                productId
            ).path
        )
    }

    fun navigateToSearchScreen() {
        navigator.navigateTo(AppDestinations.search.path)
    }

    fun onNotificationIconClick() {
        navigator.navigateTo(AppDestinations.notification.path)
    }
}

data class HomeScreenState(
    var isLoading: Boolean = false,
    var currentUser: UserResponse? = null,
    val productList: List<Product> = emptyList()
)


