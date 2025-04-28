package com.juliodigital.technicaltest.presentation.home.views

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.NeededPermissionView
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.NotPermissionView
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.VideoListView
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel
import com.juliodigital.technicaltest.utils.preferences.setPermissionRequested
import com.juliodigital.technicaltest.utils.preferences.wasPermissionRequested

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExoplayerScreen(videoViewModel: VideoViewModel) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState( permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_VIDEO
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    val permissionRequested = remember { mutableStateOf(context.wasPermissionRequested()) }

    LaunchedEffect(permissionState.status) {
        if (!permissionState.status.isGranted && !permissionState.status.shouldShowRationale && !permissionRequested.value) {
            permissionState.launchPermissionRequest()
            context.setPermissionRequested(true)
            permissionRequested.value = true
        }
    }

    LaunchedEffect(permissionState.status, permissionRequested.value) {
        if (permissionState.status.isGranted || permissionRequested.value) {
            videoViewModel.loadVideos()
            //videoViewModel.loadFolders()
        }
    }

    when {
        permissionState.status.isGranted || permissionRequested.value -> {
            VideoListView(context = context, videoViewModel)
        }
        permissionState.status.shouldShowRationale -> {
            NeededPermissionView(permissionState)
        }
        else -> {
            NotPermissionView()
        }
    }
}
