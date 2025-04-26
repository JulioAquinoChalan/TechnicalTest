package com.juliodigital.technicaltest.domain.userCase

import android.graphics.Bitmap
import com.juliodigital.technicaltest.domain.model.VideoItem
import com.juliodigital.technicaltest.data.repository.VideoRepositoryInterface

class GetAllVideosUseCase(private val repository: VideoRepositoryInterface) {
    suspend operator fun invoke(): List<VideoItem> {
        return repository.getAllVideos()
    }
}

