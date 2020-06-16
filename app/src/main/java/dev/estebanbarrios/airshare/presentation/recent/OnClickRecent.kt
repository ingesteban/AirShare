package dev.estebanbarrios.airshare.presentation.recent

import dev.estebanbarrios.airshare.data.entities.models.FileRecent

interface OnClickRecent {
    fun onClick(fileRecent: FileRecent)
}