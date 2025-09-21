package com.example.grabit.ui.flow.profile

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
class ProfileViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val navigator: AppNavigator
) : ViewModel() {

    private val _state = MutableStateFlow(EditProfileState())
    val state = _state.asStateFlow()

    private var user: UserResponse? = null


    init {
        user = userPreferences.currentUser
        _state.value = _state.value.copy(
            firstName = user?.name?.firstname ?: "",
            lastName = user?.name?.lastname ?: "",
            email = user?.email
        )
    }

    fun popBackStack() {
        navigator.navigateBack()
    }

    fun onFirstNameChanged(firstName: String) {
        _state.value = _state.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String) {
        _state.value = _state.value.copy(lastName = lastName)
    }

    fun onEmailChanged(email: String) {
        _state.value = _state.value.copy(email = email)
    }

    fun showDeleteAccountConfirmation(show: Boolean) {
        _state.value = _state.value.copy(showDeleteAccountConfirmation = show)
    }

    fun deleteAccount() {
        navigator.navigateTo(AppDestinations.login.path)
        userPreferences.currentUser = null
    }

}

data class EditProfileState(
    val saving: Boolean = false,
    val loading: Boolean = false,
    val allowSave: Boolean = false,
    val enablePhone: Boolean = false,
    val enableEmail: Boolean = false,
    val showProfileChooser: Boolean = false,
    val showDeleteAccountConfirmation: Boolean = false,
    val deletingAccount: Boolean = false,
    val error: Exception? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val profileUrl: String? = null,
    val isImageUploadInProgress: Boolean = false,
)
