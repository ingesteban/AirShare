package dev.estebanbarrios.airshare.data.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import dev.estebanbarrios.airshare.data.entities.models.TypeAdapter
import dev.estebanbarrios.airshare.data.entities.models.Video
import dev.estebanbarrios.airshare.data.entities.models.VideoGrouped
import dev.estebanbarrios.airshare.domain.repositories.VideosRepository
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDate
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDateReset
import dev.estebanbarrios.airshare.presentation.extensions.toGroupDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class VideosRepositoryImpl(private val contentResolver: ContentResolver) : VideosRepository {

    override suspend fun getVideosList(): List<VideoGrouped> {
        val videosGroupedList = mutableListOf<VideoGrouped>()

        withContext(Dispatchers.IO) {
            val query = contentResolver.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                arrayOf(
                    MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.DURATION
                ),
                "",
                emptyArray<String>(),
                ""
            )
            query?.use { cursor ->
                // Cache column indices.
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
                val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
                val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)

                while (cursor.moveToNext()) {
                    // Get values of columns for a given video.
                    val id = cursor.getLong(idColumn)
                    val file = File(cursor.getString(dataColumn))
                    val name = cursor.getString(nameColumn)
                    val duration = cursor.getLong(durationColumn)
                    val date = file.lastModified()

                    val contentUri: Uri = ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        id
                    )

                    val videoGroupedHeader = VideoGrouped(
                        TypeAdapter.HEADER,
                        date = date.millisecondToDateReset(),
                        grouped = date.millisecondToDateReset().toGroupDate()
                    )
                    if (!videosGroupedList.contains(videoGroupedHeader)) {
                        videosGroupedList += videoGroupedHeader
                    }

                    videosGroupedList += VideoGrouped(
                        TypeAdapter.ITEM,
                        Video(
                            contentUri,
                            name,
                            duration,
                            file.length(),
                            date.millisecondToDate()
                        ),
                        date.millisecondToDate(),
                        grouped = date.millisecondToDate().toGroupDate()
                    )
                }
            }

        }
        return videosGroupedList.sortedWith(compareByDescending<VideoGrouped> { it.date }.thenByDescending { it.type })

    }

}