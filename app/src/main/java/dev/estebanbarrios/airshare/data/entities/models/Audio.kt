package dev.estebanbarrios.airshare.data.entities.models

import android.net.Uri
import java.util.*

 class Audio(
    val uri: Uri,
    val name: String,
    val duration: Long,
    val size: Long,
    val date: Date,
    var isSelected: Boolean = false
)
