package com.juliodigital.technicaltest.presentation.videoFolders.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.juliodigital.technicaltest.data.VideoDataSource
import com.juliodigital.technicaltest.data.VideoRepositoryImpl
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.presentation.videoFolders.viewModel.VideoViewModel

class VideoViewModelFactory(
    private val applicationContext: Context
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            val dataSource = VideoDataSource(applicationContext)
            val repository = VideoRepositoryImpl(dataSource)
            val useCase = GetAllVideosUseCase(repository)
            return VideoViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}