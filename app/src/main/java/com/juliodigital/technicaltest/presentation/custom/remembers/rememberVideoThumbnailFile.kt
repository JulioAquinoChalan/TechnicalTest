package com.juliodigital.technicaltest.presentation.custom.remembers

import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.platform.LocalContext
import com.juliodigital.technicaltest.utils.extensions.saveBitmapToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@Composable
fun rememberVideoThumbnailFile(
    uri: Uri
): State<File?> {
    val context = LocalContext.current
    return produceState<File?>(initialValue = null, uri) {
        val cacheFile = File(context.cacheDir, "${uri.hashCode()}.jpg")
        if (cacheFile.exists()) {
            value = cacheFile
            return@produceState
        }
        try {
            val bmp = withContext(Dispatchers.IO) {
                val retriever = MediaMetadataRetriever()
                try {
                    retriever.setDataSource(context, uri)
                    retriever.getFrameAtTime(1_000_000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                } finally {
                    retriever.release()
                }
            }
            if (bmp != null) {
                withContext(Dispatchers.IO) {
                    cacheFile.saveBitmapToFile(bmp)
                }
                value = cacheFile
            } else {
                value = null
            }
        } catch (e: Exception) {
            value = null
        }
    }
}