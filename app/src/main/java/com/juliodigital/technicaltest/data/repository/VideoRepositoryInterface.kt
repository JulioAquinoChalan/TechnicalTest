package com.juliodigital.technicaltest.data.repository

import android.graphics.Bitmap
import android.net.Uri
import com.juliodigital.technicaltest.domain.model.VideoItem
import java.net.URI

interface VideoRepositoryInterface {
    suspend fun getAllVideos(): List<VideoItem>
    suspend fun getThumbnail(uri: Uri): Bitmap?
}