package com.example.grabit.ui.flow.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.grabit.R
import com.example.grabit.ui.components.AppAlertDialog
import com.example.grabit.ui.components.AppProgressIndicator
import com.example.grabit.ui.components.PrimaryTextButton
import com.example.grabit.ui.flow.profile.component.UserProfileView
import com.example.grabit.ui.theme.AppTheme

@Composable
fun EditProfileScreen() {
    Scaffold(
        topBar = {
            EditProfileToolbar()
        }
    ) {
        EditProfileScreenContent(Modifier.padding(it))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditProfileToolbar() {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = AppTheme.colorScheme.surface),
        title = {
            Text(
                text = stringResource(id = R.string.edit_profile_title),
                style = AppTheme.appTypography.subTitle1
            )
        },
        navigationIcon = {
            IconButton(onClick = { viewModel.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = ""
                )
            }
        },
        actions = {
            Text(
                text = stringResource(id = R.string.edit_profile_toolbar_save_text),
                color = if (state.allowSave) AppTheme.colorScheme.primary else AppTheme.colorScheme.textDisabled,
                style = AppTheme.appTypography.button,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = false),
                        enabled = state.allowSave,
                        onClick = {
                        }
                    )
            )
        }
    )
}

@Composable
private fun EditProfileScreenContent(modifier: Modifier) {
    val viewModel = hiltViewModel<ProfileViewModel>()
    val state by viewModel.state.collectAsState()
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.weight(0.2f))
            UserProfileView(
                Modifier.align(Alignment.CenterHorizontally),
                state.profileUrl,
                onProfileChanged = {

                },
                onProfileImageClicked = {

                },
                dismissProfileChooser = {
                },
                state.showProfileChooser,
                state.isImageUploadInProgress,
            )

            Spacer(modifier = Modifier.weight(0.6f))

            UserTextField(
                label = stringResource(R.string.edit_profile_first_name_label),
                text = state.firstName ?: "",
                onValueChange = {
                    viewModel.onFirstNameChanged(it.trimStart())
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            UserTextField(
                label = stringResource(R.string.edit_profile_last_name_label),
                text = state.lastName ?: "",
                onValueChange = {
                    viewModel.onLastNameChanged(it.trimStart())
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            UserTextField(
                label = stringResource(R.string.edit_profile_email_label),
                text = state.email ?: "",
                enabled = state.enableEmail,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                onValueChange = { viewModel.onEmailChanged(it.trimStart()) }
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryTextButton(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = ""
                    )
                },
                label = stringResource(id = R.string.settings_btn_delete_account),
                onClick = {
                    viewModel.showDeleteAccountConfirmation(true)
                },
                containerColor = AppTheme.colorScheme.containerLow,
                contentColor = AppTheme.colorScheme.alertColor,
                showLoader = state.deletingAccount,
                modifier = Modifier
                    .padding(bottom = 40.dp)
                    .wrapContentSize()
                    .align(Alignment.CenterHorizontally)
            )
        }

        if (state.loading) {
            AppProgressIndicator()
        }

        if (state.showDeleteAccountConfirmation) {
            ShowDeleteAccountDialog(viewModel)
        }
    }
}

@Composable
fun ShowDeleteAccountDialog(viewModel: ProfileViewModel) {
    AppAlertDialog(
        title = stringResource(R.string.setting_delete_account_dialogue_title_text),
        subTitle = stringResource(R.string.setting_delete_account_dialogue_message_text),
        confirmBtnText = stringResource(R.string.setting_delete_account_dialogue_confirm_btn_text),
        dismissBtnText = stringResource(R.string.common_title_cancel),
        onConfirmClick = { viewModel.deleteAccount() },
        onDismissClick = { viewModel.showDeleteAccountConfirmation(false) },
        isConfirmDestructive = true
    )
}

@Composable
fun UserTextField(
    label: String,
    text: String,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Next
    ),
    onValueChange: (value: String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val outlineColor =
        if (isFocused) AppTheme.colorScheme.primary else AppTheme.colorScheme.outline

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        Text(
            text = label,
            color = if (isFocused) AppTheme.colorScheme.primary else AppTheme.colorScheme.textDisabled,
            style = AppTheme.appTypography.caption
        )

        BasicTextField(
            value = text,
            onValueChange = { onValueChange(it) },
            enabled = enabled,
            maxLines = 1,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .fillMaxWidth(),
            singleLine = true,
            textStyle = AppTheme.appTypography.subTitle2.copy(color = AppTheme.colorScheme.textPrimary),
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            cursorBrush = SolidColor(AppTheme.colorScheme.primary)
        )
        HorizontalDivider(
            Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            color = outlineColor
        )
    }
}
