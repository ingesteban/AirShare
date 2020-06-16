package dev.estebanbarrios.airshare.domain.repositories

import dev.estebanbarrios.airshare.data.entities.models.ImagesGrouped

interface ImagesRepository {

    suspend fun getImagesList(): List<ImagesGrouped>
}