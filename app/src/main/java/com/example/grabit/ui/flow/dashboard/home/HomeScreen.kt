package com.example.grabit.ui.flow.dashboard.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.flow.dashboard.home.component.AdvertiseBanner
import com.example.grabit.ui.flow.dashboard.home.component.CollectionList
import com.example.grabit.ui.flow.dashboard.home.component.HomeCategoryView
import com.example.grabit.ui.flow.dashboard.home.component.TopProductList
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun HomeScreen() {
    val viewModel = hiltViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getTopProductList()
    }

    when {
        state.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }

        else -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                item {
                    DashboardTopAppBar(viewModel = viewModel)
                    Spacer(modifier = Modifier.height(12.dp))
                    HomeCategoryView()
                    Spacer(modifier = Modifier.height(8.dp))
                    AdvertiseBanner()
                    TopProductList(
                        text = stringResource(R.string.title_top_fashions),
                        productList = state.productList,
                        onListClick = viewModel::navigateToProductList,
                        onProductClick = viewModel::showProductDetails
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    CollectionList()
                }
            }
        }
    }

}

@Composable
fun DashboardTopAppBar(viewModel: HomeViewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = viewModel::navigateToSearchScreen,
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = colorScheme.textPrimary
            )
        }
        IconButton(
            onClick = viewModel::onNotificationIconClick,
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Notifications",
                tint = colorScheme.textPrimary
            )
        }
    }
}
