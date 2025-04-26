package com.juliodigital.technicaltest.presentation.custom.videoListView

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.juliodigital.technicaltest.domain.model.FolderItemModel
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import com.juliodigital.technicaltest.presentation.custom.thumbnailView.ThumbnailView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarpetVideosScreen(
    videos: List<VideoItemModel>,
    onFolderClick: (String) -> Unit = {}
) {
    val folders = remember(videos) {
        videos
            .groupBy { it.folderName }
            .map { (folder, items) ->
                FolderItemModel(
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
