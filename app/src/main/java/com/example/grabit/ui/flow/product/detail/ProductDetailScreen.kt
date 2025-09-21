package com.example.grabit.ui.flow.product.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun ProductDetailScreen() {
    val viewModel = hiltViewModel<ProductDetailViewModel>()
    val state by viewModel.state.collectAsState()

    val product = state.product ?: return
    val context = LocalContext.current

    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorScheme.containerLow)
                    .padding(16.dp)
                    .navigationBarsPadding(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, colorScheme.textDisabled, RoundedCornerShape(16.dp)),
                    onClick = {
                        viewModel.addToCart(product)
                        Toast.makeText(
                            context,
                            context.getString(R.string.toast_added_to_cart, product.title),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.containerNormalOnSurface,
                        contentColor = colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = "Add to Cart",
                    )
                    Text(
                        text = stringResource(R.string.btn_add_cart),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                    )
                }
                Button(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, colorScheme.textDisabled, RoundedCornerShape(16.dp)),
                    onClick = { /* buy now */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.primary,
                        contentColor = colorScheme.onPrimary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingBag,
                        contentDescription = "Buy Now",
                    )
                    Text(
                        stringResource(R.string.btn_buy_now),
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 4.dp)
                    )
                }
            }
        }
    ) { innerPadding ->

        when {
            state.isLoading -> Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                ) {
                    ProductDetailTopView(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    ProductDetailBottomView(
                        product = product,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.8f)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailTopView(modifier: Modifier, product: Product) {

    val viewModel = viewModel<ProductDetailViewModel>()
    val context = LocalContext.current

    Box(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(context = context)
                .data(product.image)
                .crossfade(true)
                .placeholder(R.drawable.iv_place_holder)
                .error(R.drawable.iv_place_holder)
                .build(),
            contentDescription = "Product Image",
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.onPrimary),
            contentScale = ContentScale.Fit
        )
        IconButton(
            onClick = { viewModel.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Go Back"
            )
        }
        Card(
            colors = CardDefaults.cardColors(
                containerColor = colorScheme.textInversePrimary
            ),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(32.dp),
            onClick = { },
            modifier = Modifier
                .absoluteOffset(y = 16.dp)
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp)
        ) {
            Icon(
                imageVector = if (product.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                tint = if (product.isFavorite) colorScheme.alertColor else colorScheme.textSecondary,
                contentDescription = "Go Back",
                modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            )
        }
    }
}

@Composable
fun ProductDetailBottomView(modifier: Modifier, product: Product) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = product.title,
            style = AppTheme.appTypography.header2,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$${product.price}",
            style = AppTheme.appTypography.subTitle2.copy(color = colorScheme.primary)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.description,
            style = AppTheme.appTypography.body2.copy(color = colorScheme.textSecondary)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingStars(rating = product.rating)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${product.rating} / 5",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun RatingStars(rating: Double, modifier: Modifier = Modifier) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier) {
        val fullStars = rating.toInt()
        val hasHalfStar = (rating % 1) >= 0.5
        val emptyStars = 5 - fullStars - if (hasHalfStar) 1 else 0

        repeat(fullStars) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Full Star",
                tint = colorScheme.permissionWarning
            )
        }
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.StarHalf,
                contentDescription = "Half Star",
                tint = colorScheme.permissionWarning
            )
        }
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Default.StarOutline,
                contentDescription = "Empty Star",
                tint = colorScheme.permissionWarning
            )
        }
    }
}
