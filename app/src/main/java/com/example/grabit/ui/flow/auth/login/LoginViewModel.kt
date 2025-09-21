package com.example.grabit.ui.flow.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.repository.UserRepository
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun passwordChange(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun navigateToRegister() {
        navigator.navigateTo(AppDestinations.signup.path)
    }

    fun login() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = "")
            try {
                val users = userRepository.getUsers()
                val user = users.find {
                    it.email.equals(_state.value.email, ignoreCase = true) &&
                            it.password == _state.value.password
                }

                Log.d("LOGIN_VIEWMODEL", "login: $user")

                userPreferences.currentUser = user

                if (user != null) {
                    navigator.navigateTo(AppDestinations.dashboard.path)
                } else {
                    _state.value = _state.value.copy(error = "Invalid email or password")
                }
            } catch (e: Exception) {
                Log.d("LOGIN_VIEWMODEL", "login: ${e.message}")
                _state.value = _state.value.copy(error = "Login failed: ${e.message}")
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

data class LoginState(
    val email: String = "",
    val password: String = "",
    val error: String = "",
    val isLoading: Boolean = false,
)
