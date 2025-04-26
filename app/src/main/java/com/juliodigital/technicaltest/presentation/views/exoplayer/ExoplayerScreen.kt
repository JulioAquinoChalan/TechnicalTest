package com.juliodigital.technicaltest.presentation.views.exoplayer

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsEndWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.juliodigital.technicaltest.domain.model.VideoItem
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import coil.compose.AsyncImage
import com.juliodigital.technicaltest.domain.model.VideoItemYoutube
import com.juliodigital.technicaltest.presentation.videoFolders.VideoFoldersActivity
import com.juliodigital.technicaltest.presentation.videoFolders.VideoViewModel
import com.juliodigital.technicaltest.presentation.videoPlayer.VideoPlayerActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExoplayerScreen(videoViewModel: VideoViewModel) {
    val context = LocalContext.current
    val permissionState = rememberPermissionState( permission =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            Manifest.permission.READ_MEDIA_VIDEO
        else
            Manifest.permission.READ_EXTERNAL_STORAGE
    )

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted) {
            permissionState.launchPermissionRequest()
        }
    }

    when {
        permissionState.status.isGranted -> {
            VideoListView(context = context, videoViewModel)
        }
        permissionState.status.shouldShowRationale -> {
            NeededPermissionView(permissionState)
        }
        else -> {
            NotPermissionView()
        }
    }
}

@Composable
fun VideoListView(
    context: Context,
    videoViewModel: VideoViewModel
) {
    val videos = videoViewModel.videos
    var viewMode by rememberSaveable { mutableStateOf(VideoViewMode.ALL_VIDEOS) }

    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        VideoViewModeSwitcher(
            currentMode = viewMode,
            onModeChange = { viewMode = it }
        )
        when(viewMode) {
            VideoViewMode.ALL_VIDEOS -> AllVideosScreen(videos.value)
            VideoViewMode.BY_FOLDER -> CarpetVideosScreen(videos.value) {
                val intent = Intent(context, VideoFoldersActivity::class.java).apply {
                    putExtra("folderName", it)
                }
                context.startActivity(intent)
            }
            VideoViewMode.BY_Youtube -> ByYoutube(context)
        }
        if(videos.value.isEmpty()) {
            NotVideos()
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AllVideosScreen(videos: List<VideoItem>) {
    LazyColumn{
        items(videos) {video ->
            VideoItemView(video = video)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarpetVideosScreen(
    videos: List<VideoItem>,
    onFolderClick: (String) -> Unit = {}
) {
    val folders = remember(videos) {
        videos
            .groupBy { it.folderName }
            .map { (folder, items) ->
                FolderItem(
                    folderName = folder,
                    videoCount = items.size,
                    thumbnailVideo = items.first()
                )
            }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(folders) { folder ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onFolderClick(folder.folderName) }
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    ThumbnailView(uri = folder.thumbnailVideo.contentUri)
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(folder.folderName, fontSize = 14.sp, maxLines = 1)
                Text("${folder.videoCount} videos", fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ThumbnailView(
    modifier: Modifier = Modifier,
    uri: Uri
) {
    val context = LocalContext.current
    val thumbnail by rememberVideoThumbnailFile(context, uri)
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

@Composable
fun rememberVideoThumbnailFile(
    context: Context,
    uri: Uri
): State<File?> {
    return produceState<File?>(initialValue = null, uri) {
        val cacheFile = File(context.cacheDir, "${uri.hashCode()}.jpg")
        if (cacheFile.exists()) {
            // Ya existe, lo usamos directamente
            value = cacheFile
        } else {
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
                        saveBitmapToFile(bmp, cacheFile)
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
}

// Función para guardar un Bitmap como archivo JPG
private fun saveBitmapToFile(bitmap: Bitmap, file: File) {
    FileOutputStream(file).use { out ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
    }
}

@Composable
fun ByYoutube(context: Context) {
    var videosYoutube = listOf<VideoItemYoutube>(
        VideoItemYoutube(url = "https://www.youtube.com/watch?v=JQcPPUd8bfs&ab_channel=Kerios", title = "Kerios"),
        VideoItemYoutube(url = "https://www.youtube.com/watch?v=7hFdqVF_pls&ab_channel=TeamSetoX", title = "TeamSetox"),

    )
    LazyColumn {
        items(videosYoutube) { video ->
            VideoItemYoutube(video, context)
        }
    }
}

@Composable
fun VideoItemYoutube(videoYoutube: VideoItemYoutube, context: Context) {
    Column (
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

@Composable
fun VideoItemView(
    modifier: Modifier = Modifier,
    video: VideoItem,
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NeededPermissionView(permissionState: PermissionState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Necesitamos acceso a tus videos para mostrarlos en esta vista.")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { permissionState.launchPermissionRequest() }) {
            Text("Conceder permiso")
        }
    }
}

@Composable
fun NotPermissionView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No tienes acceso a los videos. Habilítalo desde configuración.")
    }
}

@Composable
fun NotVideos() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No tienes videos.")
    }
}

@Composable
fun VideoViewModeSwitcher(
    currentMode: VideoViewMode,
    onModeChange: (VideoViewMode) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFE0E0E0))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        val selectedColor = Color.White
        val unselectedColor = Color.Transparent
        val textSelectedColor = Color.Black
        val textUnselectedColor = Color.DarkGray

        ToggleButton(
            text = "Todos los videos",
            selected = currentMode == VideoViewMode.ALL_VIDEOS,
            onClick = { onModeChange(VideoViewMode.ALL_VIDEOS) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )

        ToggleButton(
            text = "Por carpetas",
            selected = currentMode == VideoViewMode.BY_FOLDER,
            onClick = { onModeChange(VideoViewMode.BY_FOLDER) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )

        ToggleButton(
            text = "De Youtube",
            selected = currentMode == VideoViewMode.BY_Youtube,
            onClick = { onModeChange(VideoViewMode.BY_Youtube) },
            selectedColor = selectedColor,
            unselectedColor = unselectedColor,
            textSelectedColor = textSelectedColor,
            textUnselectedColor = textUnselectedColor,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun ToggleButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    selectedColor: Color,
    unselectedColor: Color,
    textSelectedColor: Color,
    textUnselectedColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(if (selected) selectedColor else unselectedColor)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) textSelectedColor else textUnselectedColor,
            fontSize = 14.sp
        )
    }
}
