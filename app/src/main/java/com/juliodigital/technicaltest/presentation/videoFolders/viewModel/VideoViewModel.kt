package com.juliodigital.technicaltest.presentation.videoFolders.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(private val getAllVideosUseCase: GetAllVideosUseCase) : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItemModel>>(emptyList())
    val videos: StateFlow<List<VideoItemModel>> get() = _videos

    fun loadVideos() {
        viewModelScope.launch {
            val result = getAllVideosUseCase()
            _videos.value = result
        }
    }
}