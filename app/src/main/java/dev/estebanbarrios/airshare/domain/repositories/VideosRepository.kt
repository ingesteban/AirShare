package dev.estebanbarrios.airshare.domain.repositories

import dev.estebanbarrios.airshare.data.entities.models.VideoGrouped

interface VideosRepository {

    suspend fun getVideosList(): List<VideoGrouped>
}