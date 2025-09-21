package com.example.grabit.ui.flow.product

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.components.shimmerEffect
import com.example.grabit.ui.flow.dashboard.home.component.ProductCard
import com.example.grabit.ui.theme.AppTheme.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen() {
    val viewModel = hiltViewModel<ProductListViewModel>()
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarColors(
                    containerColor = colorScheme.primary,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = colorScheme.onPrimary,
                    titleContentColor = colorScheme.onPrimary,
                    actionIconContentColor = Color.Unspecified
                ),
                title = {
                    Text("Top Fashion")
                },
                navigationIcon = {
                    IconButton(
                        onClick = viewModel::onPopBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> LoadingProductListView()

                state.productList.isEmpty() && !state.isLoading -> {
                    Text(
                        stringResource(R.string.empty_productList_title),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp)
                    ) {
                        items(state.productList) { product ->
                            ProductCard(
                                product = product,
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
                                onProductClick = {
                                    viewModel.showProductDetails(product.id)
                                },
                                onFavoriteClick = { },
                                isProductList = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingProductListView() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(12) {
            TopProductShimmerCard()
        }
    }
}

@Composable
fun TopProductShimmerCard() {
    Card(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(14.dp)
                .fillMaxWidth(0.4f)
                .padding(horizontal = 8.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(0.7f)
                .padding(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .shimmerEffect()
        )
    }
}