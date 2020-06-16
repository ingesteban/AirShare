package dev.estebanbarrios.airshare.presentation.extensions

import dev.estebanbarrios.airshare.data.entities.models.FileType

/**
 * Checks If the extension matches a specific file Type
 */
fun String.checkFileType(fileType: FileType): Boolean {
    when (fileType) {
        FileType.ALL -> {
            return true
        }
        FileType.DOCUMENTS -> {
            if (this == "doc" || this == "pdf") {
                return true
            }
        }
        FileType.AUDIO -> {
            if (this == "mp3") {
                return true
            }
        }
        FileType.VIDEO -> {
            if (this == "mp4" || this == "mkv") {
                return true
            }
        }
        FileType.IMAGE -> {
            if (this == "png" || this == "jpeg" || this == "jpg" || this == "gif") {
                return true
            }
        }
        FileType.DATABASE -> {
            if (this == "db") {
                return true
            }
        }
        FileType.CODE -> {
            if (this == "cpp" || this == "c" || this == "java" || this == "html") {
                return true
            }
        }
        FileType.APK -> {
            if (this == "apk") {
                return true
            }
        }
    }
    return false
}

fun String.checkExtensionType(): FileType {
    return when (this) {
        "apk" -> FileType.APK
        "mp3" -> FileType.AUDIO
        "cpp", "c", "java", "html" -> FileType.CODE
        "db" -> FileType.DATABASE
        "doc", "pdf" -> FileType.DOCUMENTS
        "png", "jpeg", "jpg" -> FileType.IMAGE
        "mp4", "mkv" -> FileType.VIDEO
        else -> FileType.ALL
    }
}
