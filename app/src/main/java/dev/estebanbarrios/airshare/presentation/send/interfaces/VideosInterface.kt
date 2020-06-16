package dev.estebanbarrios.airshare.presentation.send.interfaces

import dev.estebanbarrios.airshare.data.entities.models.VideoGrouped

interface VideosInterface {
    var videosList: List<VideoGrouped>?
    fun getVideos()
    fun addVideosToSend(videosList: List<VideoGrouped>)
}