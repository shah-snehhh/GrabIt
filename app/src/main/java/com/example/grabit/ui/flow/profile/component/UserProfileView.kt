package com.example.grabit.ui.flow.profile.component

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import com.example.grabit.R
import com.example.grabit.ui.components.AppProgressIndicator
import com.example.grabit.ui.theme.AppTheme
import java.io.ByteArrayOutputStream

@Composable
fun UserProfileView(
    modifier: Modifier,
    profileUrl: String?,
    onProfileChanged: (Uri?) -> Unit,
    onProfileImageClicked: () -> Unit,
    dismissProfileChooser: () -> Unit,
    showProfileChooser: Boolean = false,
    isImageUploading: Boolean = false,
) {
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {

            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                val bytes = ByteArrayOutputStream()
                it.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                val path: String = MediaStore.Images.Media.insertImage(
                    context.contentResolver,
                    it,
                    "Title",
                    null
                )
                onProfileChanged(path.toUri())
            }
        }

    if (showProfileChooser) {
        ProfileImageChooser(
            onCameraClick = {
                cameraLauncher.launch()
                dismissProfileChooser()
            },
            onGalleryClick = {
                imagePickerLauncher.launch("image/*")
                dismissProfileChooser()
            },
            onRemovePhotoClick = {
                onProfileChanged(null)
                dismissProfileChooser()
            },
            onDismissClick = dismissProfileChooser
        )
    }

    Box(modifier = modifier.size(160.dp), contentAlignment = Alignment.Center) {
        val isProfileEmpty = profileUrl.isNullOrEmpty()
        val setProfile =
            if (isProfileEmpty) R.drawable.iv_profile else profileUrl

        Image(
            painter = painterResource(R.drawable.iv_user),
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .border(1.dp, AppTheme.colorScheme.textDisabled, CircleShape)
                .background(AppTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop,
            contentDescription = "ProfileImage"
        )

        Box(
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape)
                .border(1.dp, AppTheme.colorScheme.textPrimary, CircleShape)
                .background(AppTheme.colorScheme.onPrimary, CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = AppTheme.colorScheme.onPrimaryVariant,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(8.dp)
                    .size(24.dp)
            )
        }

        if (isImageUploading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(AppTheme.colorScheme.onPrimary.copy(0.5f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AppProgressIndicator()
            }
        }
    }
}

@Composable
private fun ProfileImageChooser(
    onCameraClick: () -> Unit,
    onGalleryClick: () -> Unit,
    onRemovePhotoClick: () -> Unit,
    onDismissClick: () -> Unit,
) {
    Dialog(
        onDismissRequest = { onDismissClick() },
        DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = 600.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(28.dp))
                .background(AppTheme.colorScheme.containerNormalOnSurface),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = stringResource(R.string.edit_profile_image_chooser_camera_text),
                    style = AppTheme.appTypography.subTitle2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                Text(
                    text = stringResource(R.string.edit_profile_image_chooser_gallery_text),
                    style = AppTheme.appTypography.subTitle2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                Text(
                    text = stringResource(R.string.edit_profile_image_chooser_remove_photo_text),
                    style = AppTheme.appTypography.subTitle2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
