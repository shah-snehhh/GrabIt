package com.example.grabit.ui.flow.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.model.Address
import com.example.grabit.data.model.GeoLocation
import com.example.grabit.data.model.Name
import com.example.grabit.data.model.RegisterRequest
import com.example.grabit.data.model.UserResponse
import com.example.grabit.data.repository.AuthRepository
import com.example.grabit.data.repository.UserRepository
import com.example.grabit.data.repository.UserSession
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val navigator: AppNavigator,
    private val authRepo: AuthRepository,
    private val session: UserSession,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onEmailChange(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun onPassword(password: String) {
        _state.value = _state.value.copy(password = password)
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _state.value = _state.value.copy(confirmPassword = confirmPassword)
    }

    fun navigateToLogin() {
        navigator.navigateTo(AppDestinations.login.path)
    }

    fun register() {
        val current = _state.value
        if (current.password != current.confirmPassword) return

        _state.value = current.copy(isLoading = true)

        viewModelScope.launch {
            try {
                val response = authRepo.registerUser(
                    RegisterRequest(
                        username = current.name,
                        email = current.email,
                        password = current.password
                    )
                )

                val user = UserResponse(
                    id = response.id,
                    email = current.email,
                    username = current.name,
                    password = current.password,
                    name = Name(
                        firstname = current.name,
                        lastname = ""
                    ),
                    address = Address(
                        city = "",
                        street = "",
                        number = 0,
                        zipcode = "",
                        geolocation = GeoLocation("", "")
                    ),
                    phone = ""
                )

                session.setUser(user)
                navigator.navigateTo(AppDestinations.dashboard.path)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _state.value = _state.value.copy(isLoading = false)
            }
        }
    }
}

data class RegisterState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
)
