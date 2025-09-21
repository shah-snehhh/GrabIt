package com.example.grabit.ui.flow.dashboard.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun ProductCard(
    product: Product,
    modifier: Modifier,
    onFavoriteClick: () -> Unit = {},
    onProductClick: () -> Unit = {},
    isProductList: Boolean
) {
    val context = LocalContext.current
    Card(
        border = BorderStroke(0.5.dp, color = colorScheme.textDisabled.copy(alpha = 0.1f)),
        onClick = onProductClick,
        colors = CardDefaults.cardColors(
            containerColor = if (isProductList) Color.Unspecified else colorScheme.containerLow
        ),
        elevation = CardDefaults.cardElevation(if (isProductList) 4.dp else 0.dp),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .background(if (!isProductList) Color.Unspecified else colorScheme.containerLow)
                .fillMaxWidth()
        ) {
            Column {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(product.image)
                        .crossfade(true)
                        .placeholder(R.drawable.iv_place_holder)
                        .error(R.drawable.iv_place_holder)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .background(color = colorScheme.onPrimary)
                )
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        product.title,
                        maxLines = 1,
                        style = AppTheme.appTypography.label1.copy(color = colorScheme.textPrimary),
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        product.description,
                        style = AppTheme.appTypography.body3.copy(color = colorScheme.textSecondary),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "$${product.price}",
                            style = AppTheme.appTypography.label2.copy(color = colorScheme.textPrimary)
                        )
                        Text(
                            "⭐ ${product.rating} / 5",
                            style = AppTheme.appTypography.label2.copy(
                                color = colorScheme.secondary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }
                }
            }
            if (isProductList)
                Card(
                    onClick = onFavoriteClick,
                    colors = CardDefaults.cardColors(
                        containerColor = colorScheme.iconsBackground
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 4.dp, top = 16.dp)
                ) {
                    Image(
                        colorFilter = ColorFilter.tint(if (product.isFavorite) colorScheme.alertColor else colorScheme.surface),
                        imageVector = if (product.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = product.title,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                    )
                }
        }
    }
}