package com.example.grabit.ui.flow.dashboard.favorite

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.components.AppAlertDialog
import com.example.grabit.ui.components.ProductCardShimmer
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen() {
    val viewModel = hiltViewModel<FavoriteScreenViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(R.string.title_favorite),
                style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f)
            )
            if (state.productList.isNotEmpty())
                IconButton(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = {
                        viewModel.onDialogDismissed(true)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete All Favorites",
                        tint = colorScheme.alertColor
                    )
                }
        }
        if (state.isLoading) {
            LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
                items(7) {
                    ProductCardShimmer()
                }
            }
        } else if (state.productList.isEmpty()) {
            EmptyFavoritesView(onStartExploringClick = {

            })
        } else {
            LazyColumn(modifier = Modifier.padding(horizontal = 12.dp)) {
                items(state.productList, key = { it.id }) { product ->
                    var isVisible by remember { mutableStateOf(true) }
                    val scope = rememberCoroutineScope()

                    AnimatedVisibility(
                        visible = isVisible,
                        enter = fadeIn(tween(300)),
                        exit = fadeOut(tween(300))
                    ) {
                        FavoriteProductCard(
                            product = product,
                            onFavoriteClick = {
                                isVisible = false
                                scope.launch {
                                    delay(500L)
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.toast_removed_from_favorites),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                    }
                }
            }
        }

        if (state.isDialogShown) {
            AppAlertDialog(
                title = stringResource(R.string.title_delete_favorite_dialog),
                subTitle = stringResource(R.string.subTitle_delete_favorite_dialog),
                dismissBtnText = stringResource(R.string.common_title_cancel),
                confirmBtnText = stringResource(R.string.common_btn_delete),
                onDismissClick = { viewModel.onDialogDismissed(false) },
                onConfirmClick = {
                    state.productList.forEach {
//                        viewModel.toggleFavorite(it)
                    }
                    Toast.makeText(
                        context,
                        context.getString(R.string.toast_msg_removed_all_favorites),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                isConfirmDestructive = true
            )
        }
    }
}

@Composable
fun EmptyFavoritesView(onStartExploringClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.iv_empty_favorite),
                contentDescription = "No Favorites",
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = stringResource(R.string.empty_favorite_list_title),
                style = AppTheme.appTypography.header3.copy(color = colorScheme.textPrimary)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.empty_favorite_list_subTitle),
                style = AppTheme.appTypography.body1.copy(color = colorScheme.textSecondary),
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onStartExploringClick,
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorScheme.primary),
                modifier = Modifier.height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.btn_explore_now),
                    style = AppTheme.appTypography.button.copy(color = colorScheme.onPrimary)
                )
            }
        }
    }
}

@Composable
fun FavoriteProductCard(product: Product, onFavoriteClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorScheme.containerNormalOnSurface)
                .padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.title,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.title, style = AppTheme.appTypography.label1.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Text(
                    text = product.description,
                    style = AppTheme.appTypography.body3.copy(
                        color = colorScheme.textSecondary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "$${product.price}",
                    style = AppTheme.appTypography.caption.copy(
                        color = colorScheme.successColor, fontWeight = FontWeight.SemiBold,
                    )
                )
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Red
                )
            }
        }
    }
}
