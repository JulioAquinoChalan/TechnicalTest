package com.juliodigital.technicaltest.domain.model

data class FolderItemModel(
    val folderName: String,
    val videoCount: Int,
    val thumbnailVideo: VideoItemModel,
)