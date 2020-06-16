package dev.estebanbarrios.airshare.data.entities.models

import android.graphics.drawable.Drawable


data class App(
    val icon: Drawable,
    val packageName: String,
    val name: String,
    val size: Long,
    var isSelected: Boolean = false
)