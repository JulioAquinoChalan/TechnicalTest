package com.juliodigital.technicaltest.presentation.custom.videoListView

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.juliodigital.technicaltest.domain.model.VideoItemYoutubeModel
import com.juliodigital.technicaltest.presentation.custom.items.VideoItemYoutubeView

@Composable
fun YoutubeListView(context: Context) {
    var videosYoutube = listOf<VideoItemYoutubeModel>(
        VideoItemYoutubeModel(url = "https://www.youtube.com/watch?v=JQcPPUd8bfs&ab_channel=Kerios", title = "Kerios"),
        VideoItemYoutubeModel(url = "https://www.youtube.com/watch?v=7hFdqVF_pls&ab_channel=TeamSetoX", title = "TeamSetox"),

        )
    LazyColumn {
        items(videosYoutube) { video ->
            VideoItemYoutubeView(video, context)
        }
    }
}