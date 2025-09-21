package com.example.grabit.ui.flow.dashboard.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteScreenState())
    val state = _state.asStateFlow()


    init {
        getFavoriteProductList()
    }

    private fun getFavoriteProductList() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            try {

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }

    fun onDialogDismissed(status : Boolean) {
        _state.value = _state.value.copy(isDialogShown = status)
    }
}


data class FavoriteScreenState(
    var isLoading: Boolean = false,
    val productList: List<Product> = emptyList(),
    var isDialogShown: Boolean = false
)