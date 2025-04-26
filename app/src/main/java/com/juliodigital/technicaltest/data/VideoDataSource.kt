package com.juliodigital.technicaltest.data

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.juliodigital.technicaltest.domain.model.VideoItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URI
import kotlin.collections.set

class VideoDataSource(private val context: Context) {

    var cache: MutableMap<Uri, Bitmap?> = mutableMapOf()

    suspend fun getAllVideos(): List<VideoItemModel> = withContext(Dispatchers.IO) {
        val videos = mutableListOf<VideoItemModel>()
        val collection = MediaStore.Video.Media.EXTERNAL_CONTENT_URI

        val projection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.RELATIVE_PATH
            )
        } else {
            arrayOf(
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.TITLE
            )
        }

        context.contentResolver.query(
            collection,
            projection,
            null,
            null,
            "${MediaStore.Video.Media.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)
            val pathColumn = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val idx = cursor.getColumnIndex(MediaStore.Video.Media.RELATIVE_PATH)
                if (idx != -1) idx else null
            } else null

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val fullPath = pathColumn?.let { cursor.getString(it)?.trimEnd('/') }
                val folder = fullPath?.split("/")?.lastOrNull() ?: "Desconocido"
                val contentUri = ContentUris.withAppendedId(collection, id)
                videos.add(VideoItemModel(
                    id = id,
                    title = title,
                    contentUri = contentUri,
                    folderName = folder,
                    image = null))
            }
        }

        return@withContext videos
    }

    suspend fun getThumbnail(
        uri: Uri
    ): Bitmap? {
        cache[uri]?.let { return it }

        return withContext(Dispatchers.IO) {
            try {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(context, uri)
                val bmp = retriever.getFrameAtTime(1_000_000, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
                retriever.release()
                cache[uri] = bmp
                bmp
            } catch (e: Exception) {
                e.printStackTrace()
                cache[uri] = null
                null
            }
        }
    }

}