package com.example.grabit.ui.flow.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.components.AppInputTextField
import com.example.grabit.ui.components.PrimaryButton
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme
import com.example.grabit.ui.theme.appGradient

@Composable
fun RegisterScreen() {

    val viewModel = hiltViewModel<RegisterViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(appGradient())
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        HeaderSection()
        Spacer(modifier = Modifier.weight(0.8f))
        InputTextFieldSection(viewModel = viewModel, state = state)
        Spacer(modifier = Modifier.height(16.dp))

        PrimaryButton(
            label = stringResource(R.string.register_btn_title),
            onClick = {
                viewModel.register()
            },
            showLoader = state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.weight(0.8f))

        LoginPrompt {
            viewModel.navigateToLogin()
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun HeaderSection() {
    Spacer(modifier = Modifier.height(16.dp))
    Image(
        painter = painterResource(id = R.drawable.app_logo),
        contentDescription = "App Logo",
        modifier = Modifier.size(120.dp)
    )
    Spacer(modifier = Modifier.height(48.dp))

    Text(
        text = stringResource(R.string.register_screen_title),
        textAlign = TextAlign.Start,
        style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Text(
        text = stringResource(R.string.register_screen_description),
        textAlign = TextAlign.Start,
        style = AppTheme.appTypography.label1.copy(color = colorScheme.textDisabled),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun InputTextFieldSection(viewModel: RegisterViewModel, state: RegisterState) {

    AppInputTextField(
        value = state.name,
        onValueChange = {
            viewModel.onNameChange(it)
        },
        placeholder = {
            Text(
                stringResource(R.string.common_title_name),
                color = colorScheme.onPrimaryVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Person,
                contentDescription = "",
                tint = colorScheme.onPrimaryVariant
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    AppInputTextField(
        value = state.email,
        onValueChange = {
            viewModel.onEmailChange(it)
        },
        placeholder = {
            Text(
                stringResource(R.string.common_title_email),
                color = colorScheme.onPrimaryVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Email,
                contentDescription = "Email Icon",
                tint = colorScheme.onPrimaryVariant
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)

    )

    AppInputTextField(
        value = state.password,
        onValueChange = {
            viewModel.onPassword(it)
        },
        placeholder = {
            Text(
                stringResource(R.string.common_title_password),
                color = colorScheme.onPrimaryVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Lock,
                contentDescription = "Password Icon",
                tint = colorScheme.onPrimaryVariant
            )
        },
        isPassword = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )

    AppInputTextField(
        value = state.confirmPassword,
        onValueChange = {
            viewModel.onConfirmPasswordChange(it)
        },
        placeholder = {
            Text(
                stringResource(R.string.common_title_confirm_password),
                color = colorScheme.onPrimaryVariant.copy(alpha = 0.6f)
            )
        },
        leadingIcon = {
            Icon(
                Icons.Default.Lock,
                contentDescription = "Password Icon",
                tint = colorScheme.onPrimaryVariant
            )
        },
        isPassword = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun LoginPrompt(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.register_screen_already_account_title),
            style = AppTheme.appTypography.subTitle3.copy(color = colorScheme.textPrimary)
        )
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = stringResource(R.string.login_btn_title),
                style = AppTheme.appTypography.subTitle3.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
