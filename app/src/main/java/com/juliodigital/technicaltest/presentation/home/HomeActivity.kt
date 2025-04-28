package com.juliodigital.technicaltest.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.juliodigital.technicaltest.presentation.home.factory.HomeViewModelFactory
import com.juliodigital.technicaltest.presentation.videoFolders.factory.VideoViewModelFactory
import com.juliodigital.technicaltest.presentation.home.viewModel.HomeViewModel
import com.juliodigital.technicaltest.presentation.home.views.HomeScreen
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme

class HomeActivity : ComponentActivity() {

    private val videoViewModelFactory by lazy { VideoViewModelFactory(applicationContext) }
    private val homeViewModelFactory by lazy { HomeViewModelFactory() }

    private val viewModel: HomeViewModel by viewModels { homeViewModelFactory }
    private val videoViewModel: VideoViewModel by viewModels { videoViewModelFactory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechnicalTestTheme {
                HomeScreen(viewModel, videoViewModel)
            }

        }
    }
}



