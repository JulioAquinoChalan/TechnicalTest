package com.juliodigital.technicaltest.presentation.custom.items

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import com.juliodigital.technicaltest.presentation.custom.thumbnailView.ThumbnailView
import com.juliodigital.technicaltest.presentation.videoPlayer.VideoPlayerActivity


@Composable
fun VideoItemView(
    modifier: Modifier = Modifier,
    video: VideoItemModel,
) {
    val context = LocalContext.current
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                    putExtra("uriContent", video.contentUri)
                }
                context.startActivity(intent)
            }
    ) {
        ThumbnailView(uri = video.contentUri)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = video.title ?: "Video sin título",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}