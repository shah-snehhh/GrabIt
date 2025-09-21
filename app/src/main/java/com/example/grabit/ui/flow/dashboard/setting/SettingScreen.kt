package com.example.grabit.ui.flow.dashboard.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.grabit.R
import com.example.grabit.ui.components.AppAlertDialog
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun SettingsScreen() {

    val viewModel = hiltViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.title_settings),
            style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
            modifier = Modifier.padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        UserProfileCard()
        Spacer(modifier = Modifier.height(24.dp))
        Card(
            modifier = Modifier.padding(horizontal = 16.dp), colors = CardDefaults.cardColors(
                containerColor = colorScheme.containerLow
            )
        ) {
            profileList.forEach {
                SettingsCard(it, onClick = {})
                if (it != profileList.last()) {
                    HorizontalDivider()
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.title_other_settings),
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp),
            style = AppTheme.appTypography.header4.copy(color = colorScheme.textPrimary)
        )
        Card(
            modifier = Modifier.padding(horizontal = 16.dp), colors = CardDefaults.cardColors(
                containerColor = colorScheme.containerLow
            )
        ) {
            settingsList.forEach {
                SettingsCard(it, onClick = {
                    if (it.id == "logout") {
                        viewModel.showLogoutDialog(true)
                    }
                })
                if (it != settingsList.last()) {
                    HorizontalDivider()
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }

    if (state.isLogoutDialogVisible) {
        AppAlertDialog(
            title = stringResource(R.string.logout_dialog_title),
            subTitle = stringResource(R.string.logout_dialog_subTitle),
            confirmBtnText = stringResource(R.string.title_logout),
            dismissBtnText = stringResource(R.string.common_title_cancel),
            onDismissClick = { viewModel.showLogoutDialog(false) },
            onConfirmClick = { viewModel.onLogoutClicked() },
            isConfirmDestructive = true
        )
    }
}

@Composable
fun UserProfileCard() {
    val viewModel = hiltViewModel<SettingsViewModel>()
    val state by viewModel.state.collectAsState()

    Card(
        onClick = {
            viewModel.navigateToProfile()
        },
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(colorScheme.primary.copy(0.7f))
                .padding(4.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = R.drawable.iv_user,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp)
                    .clip(RoundedCornerShape(50)),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    state.currentUser?.username ?: "",
                    style = AppTheme.appTypography.header4.copy(color = colorScheme.onPrimary),
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    state.currentUser?.email ?: "",
                    style = AppTheme.appTypography.body2.copy(
                        color = colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(start = 8.dp, top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SettingsCard(settings: Settings, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                ripple()
                onClick()
            }
            .padding(16.dp)
    ) {
        Image(
            imageVector = settings.icon,
            contentDescription = "",
            colorFilter = ColorFilter.tint(colorScheme.textPrimary),
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = settings.title,
            modifier = Modifier.padding(start = 12.dp),
            style = AppTheme.appTypography.subTitle2.copy(color = colorScheme.textPrimary)
        )
    }
}
