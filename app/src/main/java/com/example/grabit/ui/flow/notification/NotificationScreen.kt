package com.example.grabit.ui.flow.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen() {

    val viewModel = hiltViewModel<NotificationScreenViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadNotifications()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = colorScheme.primary,
                    navigationIconContentColor = colorScheme.onPrimary,
                    titleContentColor = colorScheme.onPrimary,
                    scrolledContainerColor = Color.Unspecified,
                    actionIconContentColor = Color.Unspecified,
                ),
                navigationIcon = {
                    IconButton(onClick = viewModel::popBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                title = { Text(stringResource(R.string.title_notification)) }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    item {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                state.notificationList.isEmpty() && !state.isLoading -> {
                    item {
                        Text(
                            stringResource(R.string.title_no_notifications),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                else -> {
                    items(state.notificationList) { notification ->
                        NotificationCard(notification)
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(notification: Notification) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier.background(color = colorScheme.containerB40),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(50),
                colors = CardDefaults.cardColors(
                    containerColor = colorScheme.primary.copy(
                        0.5f
                    )
                ),
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    Icons.Default.Notifications,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    notification.title,
                    style = AppTheme.appTypography.subTitle2.copy(
                        color = colorScheme.textPrimary
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp),
                    maxLines = 2
                )
                Text(
                    notification.subTitle,
                    style = AppTheme.appTypography.body2.copy(
                        color = colorScheme.textSecondary
                    ),
                    modifier = Modifier.padding(4.dp),
                    maxLines = 4
                )
            }
        }
    }
}