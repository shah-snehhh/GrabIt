package com.example.grabit.ui.flow.dashboard.cart

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.components.PrimaryButton
import com.example.grabit.ui.components.ProductCardShimmer
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CartScreen() {
    val viewModel = hiltViewModel<CartViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadUserCart()
    }

    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.title_your_orders),
                style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
                modifier = Modifier.padding(12.dp)
            )

            when {
                state.isLoading -> ShimmerLoadingState()
                state.cartList.isEmpty() -> EmptyCartView(onStartShoppingClick = {})
                else -> {
                    CartListView(state, viewModel, context)
                }
            }
        }
        if (!state.isLoading && state.cartList.isNotEmpty()) {
            BottomPriceSection(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(color = colorScheme.surface), state = state
            )
        }
    }
}

@Composable
fun ShimmerLoadingState() {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
    ) {
        item {
            repeat(7) {
                ProductCardShimmer()
            }
        }
    }
}

@Composable
fun EmptyCartView(onStartShoppingClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Card(
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(containerColor = colorScheme.onPrimary.copy(0.5f))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.iv_empty_cart),
                    contentDescription = "",
                    modifier = Modifier
                        .size(180.dp)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.empty_cart_title),
                style = AppTheme.appTypography.header3.copy(color = colorScheme.textPrimary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.empty_cart_subTitle),
                style = AppTheme.appTypography.body1.copy(color = colorScheme.textSecondary),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onStartShoppingClick,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.btn_start_shopping),
                    style = AppTheme.appTypography.button.copy(color = colorScheme.onPrimary)
                )
            }
        }
    }
}

@Composable
fun CartListView(state: CartScreenState, viewModel: CartViewModel, context: Context) {
    LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
        itemsIndexed(
            items = state.cartList,
            key = { index, product -> "${product.id}-$index" }) { index, product ->
            var isVisible by remember { mutableStateOf(true) }
            val scope = rememberCoroutineScope()

            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(300)
                ),
                exit = slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(300)
                )
            ) {
                CartItem(product = product, viewModel = viewModel, onRemoveProduct = {
                    isVisible = false
                    scope.launch {
                        delay(500L)
                        viewModel.removeFromCart(product)
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_removed_from_favorites),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
            if (index == state.cartList.size - 1) {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun CartItem(product: Product, viewModel: CartViewModel, onRemoveProduct: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AsyncImage(
                model = product.image,
                contentDescription = product.title,
                modifier = Modifier
                    .size(116.dp)
                    .background(color = colorScheme.onPrimary),
            )

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = product.title,
                    style = AppTheme.appTypography.subTitle3.copy(
                        color = colorScheme.textPrimary,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = product.description,
                    style = AppTheme.appTypography.body3.copy(
                        color = colorScheme.textSecondary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
                )
                CartQuantityRow(product = product, viewModel = viewModel)
            }

            IconButton(
                onClick = onRemoveProduct,
                modifier = Modifier.align(Alignment.Top)
            ) {
                Image(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove",
                    colorFilter = ColorFilter.tint(
                        colorScheme.alertColor
                    )
                )
            }
        }
    }
}

@Composable
fun CartQuantityRow(product: Product, viewModel: CartViewModel) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "$${String.format("%.2f", product.price * product.quantity)}",
            style = AppTheme.appTypography.label1.copy(
                color = colorScheme.primary
            ),
            modifier = Modifier.padding(end = 12.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.primary,
            ),
            modifier = Modifier.clickable(onClick = { viewModel.decrementQuantity(product) })
        ) {
            Image(
                imageVector = Icons.Default.Remove,
                contentDescription = "Remove",
                colorFilter = ColorFilter.tint(
                    colorScheme.textInversePrimary
                ),
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp)
            )
        }
        Text(
            text = "${product.quantity}",
            style = AppTheme.appTypography.subTitle2.copy(
                color = colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.primary,
                contentColor = colorScheme.onPrimary
            ),
            modifier = Modifier.clickable(onClick = { viewModel.incrementQuantity(product) })
        ) {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "Add",
                colorFilter = ColorFilter.tint(
                    colorScheme.textInversePrimary
                ),
                modifier = Modifier
                    .size(20.dp)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun BottomPriceSection(modifier: Modifier, state: CartScreenState) {
    Card(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.background(color = colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                stringResource(R.string.title_total_price),
                style = AppTheme.appTypography.header3.copy(color = colorScheme.textPrimary),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Text(
                "$${state.totalPrice.toInt()}",
                style = AppTheme.appTypography.subTitle1.copy(color = colorScheme.primary),
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp)
            )
            PrimaryButton(
                label = stringResource(R.string.btn_pay_now),
                onClick = {},
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}
