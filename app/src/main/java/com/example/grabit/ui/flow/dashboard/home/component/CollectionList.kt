package com.example.grabit.ui.flow.dashboard.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.grabit.R
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun CollectionList() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        userScrollEnabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .height(((collectionList.size / 2) * 120).dp)
            .padding(horizontal = 8.dp)
    ) {
        items(collectionList.size) { index ->
            CollectionCard(collection = collectionList[index])
        }
    }
}

@Composable
fun CollectionCard(collection: Collection) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorScheme.containerLow),
        modifier = Modifier
            .padding(4.dp)
            .height(100.dp)
            .clickable {},
    ) {
        Box {
            AsyncImage(
                model = stringResource(collection.image),
                contentDescription = stringResource(collection.name),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(colorScheme.textDisabled.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(collection.name),
                    maxLines = 2,
                    textAlign = TextAlign.Center,
                    style = AppTheme.appTypography.header3.copy(
                        color = Color.White
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
                )
            }
        }
    }
}

data class Collection(
    val id: Int,
    val name: Int,
    val image: Int
)

val collectionList = listOf(
    Collection(
        id = 1,
        name = R.string.collection_title_one,
        image = R.string.collection_image_one
    ),
    Collection(
        id = 2,
        name = R.string.collection_title_two,
        image = R.string.collection_image_two
    ),
    Collection(
        id = 3,
        name = R.string.collection_title_three,
        image = R.string.collection_image_three
    ),
    Collection(
        id = 4,
        name = R.string.collection_title_four,
        image = R.string.collection_image_four
    ),
    Collection(
        id = 5,
        name = R.string.collection_title_five,
        image = R.string.collection_image_five
    ),
    Collection(
        id = 6,
        name = R.string.collection_title_six,
        image = R.string.collection_image_six
    )
)
