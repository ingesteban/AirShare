package dev.estebanbarrios.airshare.data.entities.models

import java.util.*

enum class FileRecentType {
    MEDIA_TYPE_AUDIO, MEDIA_TYPE_IMAGE, MEDIA_TYPE_NONE, MEDIA_TYPE_VIDEO
}

data class FileRecent(
    val id: Long,
    val type: FileRecentType,
    val date: Date,
    val audio: Audio? = null,
    val video: Video? = null,
    val images: Images? = null
)

data class FileRecentGrouped(
    val type: TypeAdapter,
    val fileRecent: FileRecent,
    val date: Date? = null,
    var isSelected: Boolean = false,
    val grouped: String
)


