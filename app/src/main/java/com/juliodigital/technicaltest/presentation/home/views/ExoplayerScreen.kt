package com.juliodigital.technicaltest.presentation.home.views

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.juliodigital.technicaltest.domain.enum.VideoViewMode
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.NeededPermissionView
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.NotPermissionView
import com.juliodigital.technicaltest.presentation.custom.exoplayerViews.VideoListView
import com.juliodigital.technicaltest.presentation.custom.videoListView.YoutubeListView
import com.juliodigital.technicaltest.presentation.custom.videoListView.AllVideosScreen
import com.juliodigital.technicaltest.presentation.custom.videoListView.CarpetVideosScreen
import com.juliodigital.technicaltest.presentation.videoFolders.VideoFoldersActivity
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel

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

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    when {
        permissionState.status.isGranted -> {
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
