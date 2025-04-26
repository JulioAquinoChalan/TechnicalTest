package com.juliodigital.technicaltest.presentation.custom.videoListView

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import com.juliodigital.technicaltest.presentation.custom.items.VideoItemView

@Composable
fun AllVideosScreen(videos: List<VideoItemModel>) {
    LazyColumn{
        items(videos) {video ->
            VideoItemView(video = video)
        }
    }
}
