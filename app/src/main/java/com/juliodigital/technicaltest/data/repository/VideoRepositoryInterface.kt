package com.juliodigital.technicaltest.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.juliodigital.technicaltest.domain.model.VideoItemModel

interface VideoRepositoryInterface {
    suspend fun getAllVideos(): List<VideoItemModel>
    suspend fun getThumbnail(uri: Uri): Bitmap?
}