package dev.estebanbarrios.airshare.presentation.send.interfaces

import dev.estebanbarrios.airshare.data.entities.models.FileRecentGrouped

interface RecentInterface {
    var recentList: List<FileRecentGrouped>?
    fun getRecent()
    fun addRecentFilesToSend(recentList: List<FileRecentGrouped>)

}