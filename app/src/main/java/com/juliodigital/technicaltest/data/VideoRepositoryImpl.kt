package com.juliodigital.technicaltest.data

import android.graphics.Bitmap
import android.net.Uri
import com.juliodigital.technicaltest.data.repository.VideoRepositoryInterface
import com.juliodigital.technicaltest.domain.model.VideoItem
import java.net.URI

class VideoRepositoryImpl(private val dataSource: VideoDataSource) : VideoRepositoryInterface {
    override suspend fun getAllVideos(): List<VideoItem> {
        return dataSource.getAllVideos()
    }
    override suspend fun getThumbnail(uri: Uri): Bitmap? {
        return dataSource.getThumbnail(uri)
    }
}