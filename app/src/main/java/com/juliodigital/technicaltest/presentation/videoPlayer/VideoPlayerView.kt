package com.juliodigital.technicaltest.presentation.videoPlayer

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.juliodigital.technicaltest.presentation.main.HomeViewModel
import com.juliodigital.technicaltest.ui.theme.TechnicalTestTheme
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlin.getValue

class VideoPlayerActivity : ComponentActivity() {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility =
            ViewGroup.SYSTEM_UI_FLAG_FULLSCREEN or
                    ViewGroup.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    ViewGroup.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        actionBar?.hide()
        val uri = intent.getParcelableExtra<Uri>("uriContent")
        val urlYoutube = intent.getStringExtra("urlYoutube")
        setContent {
            TechnicalTestTheme {
                VideoFormSelector(uri, urlYoutube)
            }
        }
    }
}

@Composable
fun VideoFormSelector(uri: Uri?, urlYoutube: String?) {
    if(uri != null) {
        VideoPlayerView(uri)
    } else if(urlYoutube != null) {
        var videoId = extractYoutubeVideoId(urlYoutube)
        VideoPlayerYoutube(videoId)
    } else {
        Text("No hay video")
    }
}

@Composable
fun VideoPlayerYoutube(videoId: String) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        factory = {
            YouTubePlayerView(it).apply {
                lifecycle.addObserver(this)

                addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                })
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .padding(10.dp)
    )
}

fun extractYoutubeVideoId(url: String): String {
    return Uri.parse(url).getQueryParameter("v") ?: ""
}


@OptIn(UnstableApi::class)
@Composable
fun VideoPlayerView(uri: Uri) {
    val context = LocalContext.current
    val loadControl = DefaultLoadControl.Builder()
        .setBufferDurationsMs(
            500,  // minBufferMs
            1500, // maxBufferMs
            250,  // bufferForPlaybackMs
            500   // bufferForPlaybackAfterRebufferMs
        )
        .build()
    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setLoadControl(loadControl)
            .build().apply {
                val mediaItem = MediaItem.fromUri(uri)
                setMediaItem(mediaItem)
            }
    }

    LaunchedEffect(Unit) {
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> exoPlayer.pause()
                Lifecycle.Event.ON_RESUME -> exoPlayer.play()
                Lifecycle.Event.ON_DESTROY -> exoPlayer.release()
                else -> {}
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true
                layoutParams =
                    ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
    )
}
