package com.juliodigital.technicaltest.presentation.custom.exoplayerViews

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.juliodigital.technicaltest.domain.enum.VideoViewMode
import com.juliodigital.technicaltest.presentation.custom.videoListView.AllVideosScreen
import com.juliodigital.technicaltest.presentation.custom.videoListView.CarpetVideosScreen
import com.juliodigital.technicaltest.presentation.custom.videoListView.VideoViewModeSwitcher
import com.juliodigital.technicaltest.presentation.custom.videoListView.YoutubeListView
import com.juliodigital.technicaltest.presentation.videoFolders.VideoFoldersActivity
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel

@Composable
fun VideoListView(
    context: Context,
    videoViewModel: VideoViewModel
) {
    val videos by videoViewModel.videos.collectAsState()
    var viewMode by rememberSaveable { mutableStateOf(VideoViewMode.ALL_VIDEOS) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        VideoViewModeSwitcher(
            currentMode = viewMode,
            onModeChange = { viewMode = it }
        )
        when(viewMode) {
            VideoViewMode.ALL_VIDEOS -> AllVideosScreen(videos)
            VideoViewMode.BY_FOLDER -> CarpetVideosScreen(videos) {
                val intent = Intent(context, VideoFoldersActivity::class.java).apply {
                    putExtra("folderName", it)
                }
                context.startActivity(intent)
            }
            VideoViewMode.BY_Youtube -> YoutubeListView(context)
        }
        if(videos.isEmpty()) {
            NotVideos()
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun NotVideos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No tienes videos.")
    }
}
