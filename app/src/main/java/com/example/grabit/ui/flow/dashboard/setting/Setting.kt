package com.example.grabit.ui.flow.dashboard.setting

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

data class Settings(
    val id: String,
    val title: String,
    val icon: ImageVector,
)

val profileList = listOf(
    Settings(
        "my_orders",
        "My Orders",
        Icons.Default.ShoppingCart
    ),
    Settings(
        "my_addresses",
        "Saved Addresses",
        Icons.Default.LocationOn
    ),
    Settings(
        "coupons",
        "Coupons",
        Icons.Default.Discount
    ),
)

val settingsList = listOf(
    Settings(
        "account",
        "Account Settings",
        Icons.Default.Settings
    ),
    Settings(
        "notifications",
        "Notifications",
        Icons.Default.Notifications
    ),
    Settings(
        "terms",
        "Terms & Conditions",
        Icons.AutoMirrored.Filled.Note
    ),
    Settings(
        "privacy",
        "Privacy Center",
        Icons.Default.PrivacyTip
    ),
    Settings(
        "help-support",
        "Help & Support",
        Icons.AutoMirrored.Filled.Help
    ),
    Settings(
        "logout",
        "Logout",
        Icons.AutoMirrored.Filled.Logout
    ),
)
