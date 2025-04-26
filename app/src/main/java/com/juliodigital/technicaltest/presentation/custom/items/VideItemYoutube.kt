package com.juliodigital.technicaltest.presentation.custom.items

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.juliodigital.technicaltest.domain.model.VideoItemYoutubeModel
import com.juliodigital.technicaltest.presentation.videoPlayer.VideoPlayerActivity

@Composable
fun VideoItemYoutubeView(videoYoutube: VideoItemYoutubeModel, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, VideoPlayerActivity::class.java).apply {
                    putExtra("urlYoutube", videoYoutube.url)
                }
                context.startActivity(intent)
            }
    ){
        Text(text = videoYoutube.title)
        Text(text = videoYoutube.url)
    }

}