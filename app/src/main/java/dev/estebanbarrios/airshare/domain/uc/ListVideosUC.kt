package dev.estebanbarrios.airshare.domain.uc

import dev.estebanbarrios.airshare.domain.repositories.VideosRepository


class ListVideosUC(
    private val videosRepository: VideosRepository
) {
    suspend fun getVideosList() = videosRepository.getVideosList()
}