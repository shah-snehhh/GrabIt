package com.example.grabit.ui.flow.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.components.AppInputTextField
import com.example.grabit.ui.components.AppProgressIndicator
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen() {
    val viewModel = hiltViewModel<SearchScreenViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    Column {
        TopAppBar(
            colors = TopAppBarColors(
                containerColor = colorScheme.primary,
                navigationIconContentColor = colorScheme.onPrimary,
                scrolledContainerColor = Color.Unspecified,
                titleContentColor = Color.Unspecified,
                actionIconContentColor = Color.Unspecified
            ),
            title = {},
            navigationIcon = {
                IconButton(viewModel::popBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null
                    )
                }
            })
        AppInputTextField(
            value = state.query,
            onValueChange = {
                viewModel.onSearchQueryChanged(it)
            },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            placeholder = { Text(stringResource(R.string.search_bar_hint)) }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.surface)
                .padding(horizontal = 8.dp)
        ) {
            items(state.productList) { product ->
                SearchProductCard(
                    product = product,
                    onClick = { viewModel.navigateToProductDetail(product.id) })
            }

            if (state.isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AppProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun SearchProductCard(product: Product, onClick: () -> Unit) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(8.dp)
            .height(140.dp)
            .fillMaxWidth(),
        onClick = onClick
    ) {
        Row {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .background(color = colorScheme.onPrimary)
            )
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = product.title,
                    style = AppTheme.appTypography.subTitle2.copy(
                        color = colorScheme.primary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                Text(
                    text = product.description, style = AppTheme.appTypography.body3,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp)
                )
                Text(
                    text = "$${product.price}",
                    style = AppTheme.appTypography.subTitle2,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp)
                )
            }
        }
    }
}
