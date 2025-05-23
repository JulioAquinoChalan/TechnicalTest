package com.juliodigital.technicaltest.presentation.videoFolders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.juliodigital.technicaltest.presentation.videoFolders.factory.VideoViewModelFactory
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel
import com.juliodigital.technicaltest.presentation.videoFolders.views.VideoFoldersScreen
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme
import kotlin.getValue

class VideoFoldersActivity : ComponentActivity() {

    private val videoViewModelFactory by lazy { VideoViewModelFactory(applicationContext) }
    private val videoViewModel: VideoViewModel by viewModels { videoViewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val folderName = intent.getStringExtra("folderName") ?: ""
        setContent {
            TechnicalTestTheme {
                VideoFoldersScreen(videoViewModel, folderName)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        videoViewModel.loadVideos()
    }
}