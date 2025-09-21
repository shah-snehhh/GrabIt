package com.example.grabit.ui

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.grabit.ui.flow.auth.login.LoginScreen
import com.example.grabit.ui.flow.auth.signup.RegisterScreen
import com.example.grabit.ui.flow.dashboard.Dashboard
import com.example.grabit.ui.flow.notification.NotificationScreen
import com.example.grabit.ui.flow.onboard.IntroScreen
import com.example.grabit.ui.flow.product.ProductListScreen
import com.example.grabit.ui.flow.product.detail.ProductDetailScreen
import com.example.grabit.ui.flow.profile.EditProfileScreen
import com.example.grabit.ui.flow.search.SearchScreen
import com.example.grabit.ui.navigation.AppDestinations
import com.example.grabit.ui.navigation.AppNavigator
import com.example.grabit.ui.navigation.slideComposable
import com.example.grabit.ui.theme.GrabItAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContent {
            GrabItAppTheme {
                Surface(
                    modifier = Modifier.Companion.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp(viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val state by viewModel.state.collectAsState()

    AppNavigator(navController = navController, viewModel.navActions)

    state.initialRoute?.let {
        NavHost(navController = navController, startDestination = state.initialRoute!!) {
            slideComposable(AppDestinations.intro.path) {
                IntroScreen()
            }
            slideComposable(AppDestinations.login.path) {
                LoginScreen()
            }
            slideComposable(AppDestinations.signup.path) {
                RegisterScreen()
            }
            slideComposable(AppDestinations.dashboard.path) {
                Dashboard()
            }
            slideComposable(AppDestinations.profile.path) {
                EditProfileScreen()
            }
            slideComposable(AppDestinations.search.path) {
                SearchScreen()
            }
            slideComposable(AppDestinations.notification.path) {
                NotificationScreen()
            }
            slideComposable(AppDestinations.productList.path) {
                ProductListScreen()
            }
            slideComposable(AppDestinations.ProductDetail.path) {
                ProductDetailScreen()
            }
        }
    }
}
