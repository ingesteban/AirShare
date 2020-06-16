package dev.estebanbarrios.airshare.data.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import dev.estebanbarrios.airshare.data.entities.models.Audio
import dev.estebanbarrios.airshare.domain.repositories.AudioRepository
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDate
import java.io.File


class AudioRepositoryImpl(private val contentResolver: ContentResolver) : AudioRepository {

    override fun getAudioList(): List<Audio> {
        val audioList = mutableListOf<Audio>()

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
                audioList += Audio(
                    contentUri,
                    name,
                    duration,
                    file.length(),
                    date.millisecondToDate()
                )
            }
        }

        return audioList.sortedByDescending { it.date }
    }

}