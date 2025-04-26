package com.juliodigital.technicaltest.domain.userCase

import com.juliodigital.technicaltest.data.repository.VideoRepositoryInterface
import com.juliodigital.technicaltest.domain.model.VideoItemModel

class GetAllVideosUseCase(private val repository: VideoRepositoryInterface) {
    suspend operator fun invoke(): List<VideoItemModel> {
        return repository.getAllVideos()
    }
}

