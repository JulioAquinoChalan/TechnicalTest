package com.juliodigital.technicaltest.presentation.videoFolders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juliodigital.technicaltest.domain.model.VideoItem
import com.juliodigital.technicaltest.domain.userCase.GetAllVideosUseCase
import com.juliodigital.technicaltest.presentation.views.exoplayer.FolderItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.groupBy

class VideoViewModel(private val getAllVideosUseCase: GetAllVideosUseCase) : ViewModel() {

    private val _videos = MutableStateFlow<List<VideoItem>>(emptyList())
    val videos: StateFlow<List<VideoItem>> get() = _videos

    private val _folders = MutableStateFlow<List<FolderItem>>(emptyList())
    val folders: StateFlow<List<FolderItem>> get() = _folders

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
                    FolderItem(
                        folderName = folder,
                        videoCount = items.size,
                        thumbnailVideo = items.first()
                    )
                }
        }
    }

}