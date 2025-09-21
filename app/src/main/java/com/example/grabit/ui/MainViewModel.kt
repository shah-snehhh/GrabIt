package com.example.grabit.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    navigator: AppNavigator,
    private val userPreferences: UserPreferences,
) : ViewModel() {

    val navActions = navigator.navigationChannel
    private val _state = MutableStateFlow(MainScreenState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val initialRoute = when {
                !userPreferences.isIntroShown() -> AppDestinations.intro.path
                userPreferences.currentUser == null -> AppDestinations.signup.path
                else -> AppDestinations.dashboard.path
            }

            _state.value = state.value.copy(initialRoute = initialRoute, isInitialRouteSet = true)
        }
    }
}

data class MainScreenState(
    val isSessionExpired: Boolean = false,
    val initialRoute: String? = null,
    val isInitialRouteSet: Boolean = false,
)
