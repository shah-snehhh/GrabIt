package com.example.grabit.ui.flow.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grabit.data.storage.UserPreferences
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val userPreferences: UserPreferences,
    private val navigator: AppNavigator
) : ViewModel() {

    fun completedIntro() = viewModelScope.launch {
        userPreferences.setIntroShown(true)
        navigator.navigateTo(
            route = AppDestinations.signup.path,
            popUpToRoute = AppDestinations.intro.path,
            inclusive = true
        )
    }
}
