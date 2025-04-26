package com.juliodigital.technicaltest.presentation.views.exoplayer

import android.graphics.Bitmap
import com.juliodigital.technicaltest.domain.model.VideoItem

data class FolderItem(
    val folderName: String,
    val videoCount: Int,
    val thumbnailVideo: VideoItem,
)