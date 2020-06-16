package dev.estebanbarrios.airshare.data.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import dev.estebanbarrios.airshare.data.entities.models.*
import dev.estebanbarrios.airshare.domain.repositories.RecentRepository
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDate
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDateReset
import dev.estebanbarrios.airshare.presentation.extensions.toGroupDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.*


class RecentRepositoryImpl(private val contentResolver: ContentResolver) : RecentRepository {

    private var dateBefore: Calendar = Calendar.getInstance().apply {
        time = Date()
        add(Calendar.DATE, -30)
    }

    override suspend fun getRecentFiles(): List<FileRecentGrouped> {
        val fileList = mutableListOf<FileRecentGrouped>()
        withContext(Dispatchers.IO) {
            fileList.addAll(getImage())
            fileList.addAll(getAudio(fileList))
            fileList.addAll(getVideo(fileList))
        }
        return fileList.sortedWith(compareByDescending<FileRecentGrouped> { it.date }.thenByDescending { it.type })
    }

    private fun getImage(): List<FileRecentGrouped> {
        val imageGroupedList = mutableListOf<FileRecentGrouped>()

        val query = contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA
            ),
            "",
            emptyArray<String>(),
            ""
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (cursor.moveToNext()) {
                // Get values of columns for a given image.
                val id = cursor.getLong(idColumn)
                val file = File(cursor.getString(dataColumn))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val date = file.lastModified()

                if (date.millisecondToDate() > dateBefore.time) {

                    val fileRecentGroupedHeader = FileRecentGrouped(
                        TypeAdapter.HEADER,
                        date = date.millisecondToDateReset(),
                        grouped = date.millisecondToDateReset().toGroupDate(),
                    )
                    if (!imageGroupedList.contains(fileRecentGroupedHeader)) {
                        imageGroupedList += fileRecentGroupedHeader
                    }

                    // that represents the media file.
                    imageGroupedList += FileRecentGrouped(
                        TypeAdapter.ITEM,
                        FileRecent(
                            id,
                            FileRecentType.MEDIA_TYPE_IMAGE,
                            date.millisecondToDate(),
                            images = Images(
                                contentUri,
                                file.length(),
                                date.millisecondToDate()
                            )
                        ),
                        date.millisecondToDate(),
                        grouped = date.millisecondToDate().toGroupDate()
                    )
                }
            }
        }

        return imageGroupedList

    }

    private fun getAudio(fileList:List<FileRecentGrouped>): List<FileRecentGrouped> {
        val audioRecentGrouped = mutableListOf<FileRecentGrouped>()

        val query = contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA
            ),
            "",
            emptyArray<String>(),
            ""
        )
        query?.use { cursor ->
            // Cache column indices.
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                // Get values of columns for a given audio.
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val file = File(cursor.getString(dataColumn))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val date = file.lastModified()

                // that represents the media file.
                if (date.millisecondToDate() > dateBefore.time) {
                    val fileRecentGroupedHeader = FileRecentGrouped(
                        TypeAdapter.HEADER,
                        date = date.millisecondToDateReset(),
                        grouped = date.millisecondToDateReset().toGroupDate()
                    )
                    if (!fileList.contains(fileRecentGroupedHeader)) {
                        audioRecentGrouped += fileRecentGroupedHeader
                    }

                    audioRecentGrouped +=
                        FileRecentGrouped(
                            TypeAdapter.ITEM,
                            FileRecent(
                                id,
                                FileRecentType.MEDIA_TYPE_AUDIO,
                                date.millisecondToDate(),
                                audio = Audio(
                                    contentUri,
                                    name,
                                    duration,
                                    file.length(),
                                    date.millisecondToDate()
                                )
                            ),
                            date.millisecondToDate(),
                            grouped = date.millisecondToDate().toGroupDate()
                        )
                }
            }
        }

        return audioRecentGrouped
    }

    private fun getVideo(fileList:List<FileRecentGrouped>): List<FileRecentGrouped> {
        val videoGroupedList = mutableListOf<FileRecentGrouped>()

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
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val file = File(cursor.getString(dataColumn))

                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                val date = file.lastModified()

                if (date.millisecondToDate() > dateBefore.time) {
                    val fileRecentGroupedHeader = FileRecentGrouped(
                        TypeAdapter.HEADER,
                        date = date.millisecondToDateReset(),
                        grouped = date.millisecondToDateReset().toGroupDate()
                    )
                    if (!fileList.contains(fileRecentGroupedHeader)) {
                        videoGroupedList += fileRecentGroupedHeader
                    }

                    videoGroupedList += FileRecentGrouped(
                        TypeAdapter.ITEM,
                        FileRecent(
                            id,
                            FileRecentType.MEDIA_TYPE_VIDEO,
                            date.millisecondToDate(),
                            video = Video(
                                contentUri,
                                name,
                                duration,
                                file.length(),
                                date.millisecondToDate()
                            )
                        ),
                        date.millisecondToDate(),
                        grouped = date.millisecondToDate().toGroupDate()
                    )
                }
            }
        }

        return videoGroupedList
    }

}