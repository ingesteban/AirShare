package dev.estebanbarrios.airshare.data.entities.models

import android.net.Uri
import java.util.*

data class Images(
    val uri: Uri,
    val size: Long,
    val date: Date? = null
)

data class ImagesGrouped(
    val type: TypeAdapter,
    val image: Images? = null,
    val date: Date? = null,
    var isSelected: Boolean = false,
    val grouped: String
)
