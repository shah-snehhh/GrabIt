package com.example.grabit.ui.flow.dashboard.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grabit.R
import com.example.grabit.data.model.Product
import com.example.grabit.ui.theme.AppTheme

@Composable
fun TopProductList(
    text: String,
    productList: List<Product>,
    onListClick: () -> Unit,
    onProductClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text,
                modifier = Modifier.padding(start = 8.dp),
                style = AppTheme.appTypography.subTitle2.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(
                onClick = onListClick,
                modifier = Modifier.padding(end = 8.dp)
            )
            { Text(stringResource(R.string.btn_see_all), style = AppTheme.appTypography.button) }
        }
        LazyRow {
            items(productList.size) {
                ProductCard(
                    product = productList[it], modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        .width(160.dp)
                        .aspectRatio(5f / 7f),
                    onProductClick = { onProductClick(productList[it].id) },
                    onFavoriteClick = { },
                    isProductList = false
                )
            }
        }
    }
}
