package com.example.grabit.ui.flow.dashboard

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.grabit.R
import com.example.grabit.ui.flow.dashboard.cart.CartScreen
import com.example.grabit.ui.flow.dashboard.favorite.FavoriteScreen
import com.example.grabit.ui.flow.dashboard.home.HomeScreen
import com.example.grabit.ui.flow.dashboard.setting.SettingsScreen
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.tabComposable
import com.example.grabit.ui.theme.AppTheme

@Composable
fun Dashboard() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = AppTheme.colorScheme.surface,
        content = {
            Box(
                modifier = Modifier.padding(it)
            ) {
                HomeScreenContent(navController)
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(tween(100)) { it },
                exit = slideOutVertically(tween(100)) { it }
            ) {
                HomeBottomBar(navController)
            }
        }
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreenContent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.home.path,
    ) {
        tabComposable(AppDestinations.home.path) {
            HomeScreen()
        }

        tabComposable(AppDestinations.favorites.path) {
            FavoriteScreen()
        }
        tabComposable(AppDestinations.cart.path) {
            CartScreen()
        }
        tabComposable(AppDestinations.settings.path) {
            SettingsScreen()
        }
    }
}

@Composable
fun HomeBottomBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedIndex = when (currentRoute) {
        AppDestinations.home.path -> 0
        AppDestinations.favorites.path -> 1
        AppDestinations.cart.path -> 2
        AppDestinations.settings.path -> 3
        else -> 0
    }

    fun navigateTo(route: String) {
        navController.navigate(route) {
            navController.graph.startDestinationRoute.let { route ->
                route?.let {
                    popUpTo(it) {
                        saveState = true
                    }
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    BottomAppBar(
        contentColor = AppTheme.colorScheme.primary,
        containerColor = AppTheme.colorScheme.containerInverseHigh,
    ) {
        NavItem(HomeTab.Home, selectedIndex == 0) {
            navigateTo(AppDestinations.home.path)
        }
        NavItem(HomeTab.Favorite, selectedIndex == 1) {
            navigateTo(AppDestinations.favorites.path)
        }
        NavItem(HomeTab.Cart, selectedIndex == 2) {
            navigateTo(AppDestinations.cart.path)
        }
        NavItem(HomeTab.Settings, selectedIndex == 3) {
            navigateTo(AppDestinations.settings.path)
        }
    }
}

@Composable
private fun RowScope.NavItem(
    screen: HomeTab,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val label = when (screen.route) {
        HomeTab.Home.route -> stringResource(R.string.home_tab_label_home)
        HomeTab.Favorite.route -> stringResource(R.string.home_tab_label_favorites)
        HomeTab.Cart.route -> stringResource(R.string.home_tab_label_cart)
        HomeTab.Settings.route -> stringResource(R.string.home_tab_label_settings)
        else -> stringResource(R.string.home_tab_label_home)
    }

    NavigationBarItem(
        icon = {
            Icon(
                imageVector = if (isSelected) screen.resourceIdFilled else screen.resourceIdLine,
                null,
                modifier = Modifier.size(24.dp)
            )
        },
        selected = isSelected,
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = AppTheme.colorScheme.containerNormalOnSurface,
            selectedIconColor = AppTheme.colorScheme.primary,
            selectedTextColor = AppTheme.colorScheme.primary,
            unselectedIconColor = AppTheme.colorScheme.textDisabled,
            unselectedTextColor = AppTheme.colorScheme.textDisabled
        ),
        alwaysShowLabel = true,
        onClick = onClick,
        label = {
            Text(
                text = label,
                style = AppTheme.appTypography.label3,
                fontSize = 10.sp,
                overflow = TextOverflow.Ellipsis
            )
        }
    )
}

sealed class HomeTab(
    val route: String,
    val resourceIdLine: ImageVector,
    val resourceIdFilled: ImageVector
) {
    data object Home : HomeTab(
        "Home",
        Icons.Outlined.Home,
        Icons.Filled.Home
    )

    data object Favorite : HomeTab(
        "Favorite",
        Icons.Outlined.Favorite,
        Icons.Filled.Favorite
    )

    data object Cart : HomeTab(
        "Cart",
        Icons.Outlined.ShoppingCart,
        Icons.Filled.ShoppingCart
    )

    data object Settings : HomeTab(
        "Settings",
        Icons.Outlined.Settings,
        Icons.Filled.Settings
    )
}
