package com.juliodigital.technicaltest.presentation.videoFolders.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.domain.model.FolderItemModel
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VideoViewModel(private val getAllVideosUseCase: GetAllVideosUseCase) : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItemModel>>(emptyList())
    val videos: StateFlow<List<VideoItemModel>> get() = _videos

    private val _folders = MutableStateFlow<List<FolderItemModel>>(emptyList())
    val folders: StateFlow<List<FolderItemModel>> get() = _folders

    fun loadVideos() {
        viewModelScope.launch {
            val result = getAllVideosUseCase()
            _videos.value = result
        }
    }

    fun loadFolders() {
        viewModelScope.launch {
            val result = getAllVideosUseCase()
            _folders.value = result.groupBy { it.folderName }
                .map { (folder, items) ->
                    FolderItemModel(
                        folderName = folder,
                        videoCount = items.size,
                        thumbnailVideo = items.first()
                    )
                }
        }
    }

}