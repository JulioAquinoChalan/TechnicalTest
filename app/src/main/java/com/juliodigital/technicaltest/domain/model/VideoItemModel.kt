package com.juliodigital.technicaltest.domain.model

import android.graphics.Bitmap
import android.net.Uri

data class VideoItemModel(
    val id: Long,
    val title: String,
    val contentUri: Uri,
    val folderName: String,
    val image: Bitmap? = null
)

