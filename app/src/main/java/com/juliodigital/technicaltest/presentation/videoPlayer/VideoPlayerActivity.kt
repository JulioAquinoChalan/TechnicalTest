package com.juliodigital.technicaltest.presentation.videoPlayer

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.juliodigital.technicaltest.presentation.videoPlayer.views.VideoFormSelectorScreen
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme

class VideoPlayerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            ViewGroup.SYSTEM_UI_FLAG_FULLSCREEN or
                    ViewGroup.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    ViewGroup.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        actionBar?.hide()
        val uri = intent.getParcelableExtra<Uri>("uriContent")
        val urlYoutube = intent.getStringExtra("urlYoutube")
        var idYoutube = extractYoutubeVideoId(urlYoutube ?: "")
        setContent {
            TechnicalTestTheme {
                VideoFormSelectorScreen(uri, idYoutube)
            }
        }
    }

    private fun extractYoutubeVideoId(url: String): String {
        return Uri.parse(url).getQueryParameter("v") ?: ""
    }
}
