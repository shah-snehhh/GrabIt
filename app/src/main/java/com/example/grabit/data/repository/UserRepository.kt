package com.example.grabit.data.repository

import com.example.grabit.data.retrofit.UserApi
import com.example.grabit.data.model.RegisterResponse
import com.example.grabit.data.model.UserResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

class UserRepository @Inject constructor(
    private val userApi: UserApi,
) {
    suspend fun getUsers(): List<UserResponse> {
        return userApi.getUsers()
    }
}

@Singleton
class UserSession @Inject constructor() {
    private val _user = MutableStateFlow<UserResponse?>(null)
    val user: StateFlow<UserResponse?> = _user

    fun setUser(user: UserResponse) {
        _user.value = user
    }

    fun clearUser() {
        _user.value = null
    }
}
