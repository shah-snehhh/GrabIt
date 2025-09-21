package com.example.grabit.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.grabit.ui.theme.AppTheme

@Composable
fun AppProgressIndicator(
    color: Color = AppTheme.colorScheme.primary,
    strokeWidth: Dp = ProgressIndicatorDefaults.CircularStrokeWidth
) {
    CircularProgressIndicator(
        color = color,
        strokeWidth = strokeWidth,
        modifier = Modifier
            .height(20.dp)
            .width(20.dp)
    )
}