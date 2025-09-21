package com.example.grabit.ui.flow.dashboard.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.grabit.R
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun HomeCategoryView() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
        ) {
            items(categoryList.size) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = colorScheme.containerInverseHigh),
                        shape = RoundedCornerShape(32.dp)
                    ) {
                        Image(
                            painter = painterResource(categoryList[it].image),
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(4.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Text(
                        categoryList[it].title,
                        style = AppTheme.appTypography.label2,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

data class Category(
    val id: Int,
    val image: Int,
    val title: String
)

val categoryList = listOf(
    Category(1, R.drawable.ic_cat_1, "Clothes"),
    Category(2, R.drawable.ic_cat_2, "Beauty"),
    Category(3, R.drawable.ic_cat_3, "Shoes"),
    Category(4, R.drawable.ic_cat_4, "Perfume"),
    Category(5, R.drawable.ic_cat_5, "Electronics"),
    Category(6, R.drawable.ic_cat_6, "Furniture"),
    Category(7, R.drawable.ic_cat_7, "Books"),
    Category(8, R.drawable.ic_cat_8, "Sports"),
)
