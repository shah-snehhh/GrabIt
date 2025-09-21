package com.example.grabit.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme

@Composable
fun AppInputTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
) {
    var passwordVisible by remember { mutableStateOf(false) }

    val visualTransformation = if (isPassword && !passwordVisible) {
        PasswordVisualTransformation()
    } else {
        VisualTransformation.None
    }

    val trailingIcon: @Composable (() -> Unit)? = if (isPassword) {
        {
            val icon = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility

            Icon(
                imageVector = icon,
                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                tint = colorScheme.onPrimaryVariant,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { passwordVisible = !passwordVisible }
            )
        }
    } else null

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType).copy(
            imeAction = ImeAction.Next
        ),
        shape = RoundedCornerShape(16.dp),
        visualTransformation = visualTransformation,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorScheme.onPrimary.copy(alpha = 0.9f),
            unfocusedContainerColor = colorScheme.onPrimary.copy(alpha = 0.8f),
            focusedTextColor = colorScheme.onPrimaryVariant,
            unfocusedTextColor = colorScheme.onPrimaryVariant.copy(alpha = 0.9f),
        ),
        singleLine = true
    )
}
