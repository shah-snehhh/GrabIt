package com.example.grabit.ui.flow.dashboard.setting

import androidx.lifecycle.ViewModel
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(SettingState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(currentUser = userPreferences.currentUser)
    }

    fun navigateToProfile() {
        navigator.navigateTo(AppDestinations.profile.path, inclusive = false)
    }

    fun showLogoutDialog(show: Boolean) {
        _state.value = _state.value.copy(isLogoutDialogVisible = show)
    }

    fun onLogoutClicked() {
        _state.value = _state.value.copy(currentUser = null)
        navigator.navigateTo(AppDestinations.login.path, inclusive = false)
    }
}

data class SettingState(
    var isLogoutDialogVisible: Boolean = false,
    var currentUser: UserResponse? = null
)