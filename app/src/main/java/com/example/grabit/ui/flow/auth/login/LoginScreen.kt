package com.example.grabit.ui.flow.auth.login

import android.widget.Toast
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.components.AppInputTextField
import com.example.grabit.ui.components.PrimaryButton
import com.example.grabit.ui.components.PrimaryOutlinedButton
import com.example.grabit.ui.components.PrimaryTextButton
import com.example.grabit.ui.theme.AppTheme
import com.example.grabit.ui.theme.AppTheme.colorScheme
import com.example.grabit.ui.theme.appGradient

@Composable
fun LoginScreen() {
    val viewModel = hiltViewModel<LoginViewModel>()
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(appGradient())
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.8f))
        HeaderSection()
        Spacer(modifier = Modifier.weight(1f))
        InputTextFieldSection(viewModel = viewModel, state = state)
        ForgotPasswordText(
            onClick = {},
            modifier = Modifier.align(Alignment.End)
        )
        PrimaryButton(
            label = stringResource(R.string.login_btn_title),
            onClick = {
                viewModel.login()
            },
            showLoader = state.isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))
        SocialLoginSection()
        Spacer(modifier = Modifier.weight(1f))
        SignUpPrompt {
            viewModel.navigateToRegister()
        }
        Spacer(modifier = Modifier.weight(1f))

        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
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
    Spacer(modifier = Modifier.height(64.dp))

    Text(
        text = stringResource(R.string.login_screen_title),
        textAlign = TextAlign.Start,
        style = AppTheme.appTypography.header1.copy(color = colorScheme.primary),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Text(
        text = stringResource(R.string.login_screen_description),
        textAlign = TextAlign.Start,
        style = AppTheme.appTypography.label1.copy(color = colorScheme.textDisabled),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )

    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun InputTextFieldSection(viewModel: LoginViewModel, state: LoginState) {

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
        modifier = Modifier.fillMaxWidth(),
        keyboardType = KeyboardType.Email
    )

    AppInputTextField(
        value = state.password,
        onValueChange = {
            viewModel.passwordChange(it)
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
            .padding(top = 16.dp),
        keyboardType = KeyboardType.Password
    )
}

@Composable
private fun ForgotPasswordText(onClick: () -> Unit, modifier: Modifier) {
    PrimaryTextButton(
        containerColor = Color.Transparent,
        label = stringResource(R.string.title_forgot_password),
        onClick = onClick,
        modifier = modifier.padding(top = 16.dp)
    )
    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun SocialLoginSection() {

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.login_other_method_title),
            textAlign = TextAlign.Center,
            style = AppTheme.appTypography.label1.copy(color = colorScheme.textSecondary),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        HorizontalDivider(modifier = Modifier.weight(1f))
    }

    Spacer(modifier = Modifier.height(32.dp))

    PrimaryOutlinedButton(
        contentColor = colorScheme.onPrimaryVariant,
        containerColor = colorScheme.onPrimary,
        outlineColor = colorScheme.onPrimaryVariant,
        label = stringResource(R.string.btn_continue_with_google),
        onClick = {},
        icon = {
            Image(
                painterResource(R.drawable.ic_sign_in_google_logo),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )

    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun SignUpPrompt(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.login_screen_account_create_title),
            style = AppTheme.appTypography.subTitle3.copy(
                color = colorScheme.textPrimary,
                fontWeight = FontWeight.W400
            )
        )
        TextButton(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        ) {
            Text(
                text = stringResource(R.string.login_screen_title_register),
                style = AppTheme.appTypography.subTitle3.copy(
                    color = colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
