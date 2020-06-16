package dev.estebanbarrios.airshare.data.repositories

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import dev.estebanbarrios.airshare.data.entities.models.Images
import dev.estebanbarrios.airshare.data.entities.models.ImagesGrouped
import dev.estebanbarrios.airshare.data.entities.models.TypeAdapter
import dev.estebanbarrios.airshare.domain.repositories.ImagesRepository
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDate
import dev.estebanbarrios.airshare.presentation.extensions.millisecondToDateReset
import dev.estebanbarrios.airshare.presentation.extensions.toGroupDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


class ImagesRepositoryImpl(private val contentResolver: ContentResolver) : ImagesRepository {

    override suspend fun getImagesList(): List<ImagesGrouped> {
        val imagesGroupedList = mutableListOf<ImagesGrouped>()

        withContext(Dispatchers.IO) {

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
                    val imagesGroupedHeader = ImagesGrouped(
                        TypeAdapter.HEADER,
                        date = date.millisecondToDateReset(),
                        grouped = date.millisecondToDateReset().toGroupDate()
                    )
                    if (!imagesGroupedList.contains(imagesGroupedHeader)) {
                        imagesGroupedList += imagesGroupedHeader
                    }

                    imagesGroupedList += ImagesGrouped(
                        TypeAdapter.ITEM,
                        Images(
                            contentUri,
                            file.length(),
                            date.millisecondToDate()
                        ),
                        date.millisecondToDate(),
                        grouped = date.millisecondToDate().toGroupDate()
                    )
                }
            }
        }

        return imagesGroupedList.sortedWith(compareByDescending<ImagesGrouped> { it.date }.thenByDescending { it.type })
    }

}
