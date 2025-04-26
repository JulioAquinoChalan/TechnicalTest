package com.juliodigital.technicaltest.presentation

import android.content.Context
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.juliodigital.technicaltest.data.VideoDataSource
import com.juliodigital.technicaltest.data.VideoRepositoryImpl
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.presentation.main.HomeViewModel
import com.juliodigital.technicaltest.presentation.videoFolders.VideoViewModel

class AppViewModelFactory(
    var context: Context
) : AbstractSavedStateViewModelFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        state: SavedStateHandle
    ): T {
        val dataSource = VideoDataSource(context)
        val repository = VideoRepositoryImpl(dataSource)
        val useCase = GetAllVideosUseCase(repository)
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel() as T
            VideoViewModel::class.java -> VideoViewModel(useCase) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}