package com.juliodigital.technicaltest.presentation.custom.thumbnailView

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.juliodigital.technicaltest.presentation.custom.remembers.rememberVideoThumbnailFile


@Composable
fun ThumbnailView(
    modifier: Modifier = Modifier,
    uri: Uri
) {
    val thumbnail by rememberVideoThumbnailFile(uri)
    Box(modifier.size(150.dp)) {
        thumbnail?.let {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = thumbnail,
                contentScale = ContentScale.Crop,
                contentDescription = "Thumbnail"
            )
        } ?: Text("Cargando...", fontSize = 12.sp)
    }
}