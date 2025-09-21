package com.example.grabit.ui.flow.dashboard.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.grabit.R
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun AdvertiseBanner() {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.containerLow
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.iv_advertise_banner_1),
                contentDescription = "Search",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 160.dp)
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.advertise_banner_one_title),
                    style = AppTheme.appTypography.header3.copy(color = colorScheme.onPrimary, fontWeight = FontWeight.W600),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.advertise_banner_one_subTitle),
                    style = AppTheme.appTypography.subTitle3.copy(color = colorScheme.onPrimaryVariant, fontWeight = FontWeight.SemiBold),
                )
                Spacer(modifier = Modifier.height(8 .dp))
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorScheme.onPrimaryVariant,
                        contentColor = colorScheme.onPrimary
                    ),
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(start = 4.dp)
                ) {
                    Text(
                        text = stringResource(R.string.btn_shop_now),
                    )
                }
            }
        }
    }
}
