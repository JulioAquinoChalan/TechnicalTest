package com.juliodigital.technicaltest.presentation.videoFolders.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juliodigital.technicaltest.presentation.custom.items.VideoItemView
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel

@Composable
fun VideoFoldersScreen(videoViewModel: VideoViewModel, folderName: String) {
    val videos by videoViewModel.videos.collectAsState()
    val filtered = remember(videos, folderName) {
        videos.filter {
            it.folderName == folderName
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = folderName,
            modifier = Modifier.padding(16.dp),
            fontSize = 20.sp
        )

        LazyColumn {
            items(filtered) { video ->
                VideoItemView(
                    video = video
                )
            }
        }
    }
}