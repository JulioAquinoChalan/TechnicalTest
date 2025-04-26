package com.juliodigital.technicaltest.data

import android.graphics.Bitmap
import android.net.Uri
import com.juliodigital.technicaltest.data.repository.VideoRepositoryInterface
import com.juliodigital.technicaltest.domain.model.VideoItemModel

class VideoRepositoryImpl(private val dataSource: VideoDataSource) : VideoRepositoryInterface {
    override suspend fun getAllVideos(): List<VideoItemModel> {
        return dataSource.getAllVideos()
    }
    override suspend fun getThumbnail(uri: Uri): Bitmap? {
        return dataSource.getThumbnail(uri)
    }
}