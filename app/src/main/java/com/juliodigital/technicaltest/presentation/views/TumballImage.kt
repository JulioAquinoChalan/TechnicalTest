package com.juliodigital.technicaltest.presentation.views

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext


fun getVideoThumbnail(context: Context, uri: Uri): Bitmap? {
    val retriever = MediaMetadataRetriever()
    return try {
        retriever.setDataSource(context, uri)
        retriever.getFrameAtTime(0) // El primer frame
    } catch (e: Exception) {
        e.printStackTrace()
        null
    } finally {
        retriever.release()
    }
}

@Composable
fun rememberVideoThumbnail(uri: Uri?): Bitmap? {
    val context = LocalContext.current

    var thumbnail = remember(uri) { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(uri) {
        if (uri != null) {
            thumbnail.value = getVideoThumbnail(context, uri)
        }
    }

    return thumbnail.value
}

@Composable
fun VideoThumbnail(uri: Uri) {
    val thumbnail = rememberVideoThumbnail(uri)

    if (thumbnail != null) {
        Image(
            bitmap = thumbnail.asImageBitmap(),
            contentDescription = null
        )
    } else {
        Text("Loading thumbnail...")
    }
}