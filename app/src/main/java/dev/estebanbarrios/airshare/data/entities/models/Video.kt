package dev.estebanbarrios.airshare.data.entities.models

import android.net.Uri
import java.util.*

data class Video(
    val uri: Uri,
    val name: String,
    val duration: Long,
    val size: Long,
    val date: Date
)

data class VideoGrouped(
    val type: TypeAdapter,
    val video: Video? = null,
    val date: Date? = null,
    var isSelected: Boolean = false,
    val grouped: String
)