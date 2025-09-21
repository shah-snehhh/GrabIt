package com.example.grabit.ui.flow.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationScreenViewModel @Inject constructor(
    private val navigator: AppNavigator,
) : ViewModel() {

    private val _state = MutableStateFlow(NotificationScreenState())
    val state = _state.asStateFlow()


    suspend fun loadNotifications() {
        _state.value = _state.value.copy(isLoading = true)
        delay(500L)
        viewModelScope.launch {
            val notifications = notificationList
            _state.value = _state.value.copy(notificationList = notifications)
        }
        _state.value = _state.value.copy(isLoading = false)
    }

    fun popBack(){
        navigator.navigateBack()
    }

}

data class NotificationScreenState(
    val isLoading: Boolean = false,
    val notificationList: List<Notification> = emptyList(),

    )

data class Notification(
    val id: Int,
    val title: String,
    val subTitle: String,
)

val notificationList = listOf<Notification>(
    Notification(
        1,
        "Test Notification",
        "Hello! Hope you're doing well. This is a test notification. Keep Shopping with GrabIt."
    )
)