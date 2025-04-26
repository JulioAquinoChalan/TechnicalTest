package com.juliodigital.technicaltest.presentation.videoFolders

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juliodigital.technicaltest.data.VideoDataSource
import com.juliodigital.technicaltest.data.VideoRepositoryImpl
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.presentation.views.exoplayer.VideoItemView
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme
import kotlin.getValue

class VideoFoldersActivity : ComponentActivity() {

    private val viewModel: VideoViewModel by lazy {
        val dataSource = VideoDataSource(this)
        val repository = VideoRepositoryImpl(dataSource)
        val useCase = GetAllVideosUseCase(repository)
        VideoViewModel(useCase)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val folderName = intent.getStringExtra("folderName") ?: ""
        setContent {
            TechnicalTestTheme {
                VideoFoldersView(viewModel, folderName)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadVideos()
    }
}

@Composable
fun VideoFoldersView(viewModel: VideoViewModel, folderName: String) {
    val videos by viewModel.videos.collectAsState()
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
                VideoItemView(video = video)
            }
        }
    }
}