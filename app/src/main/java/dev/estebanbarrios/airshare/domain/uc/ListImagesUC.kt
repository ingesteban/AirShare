package dev.estebanbarrios.airshare.domain.uc

import dev.estebanbarrios.airshare.domain.repositories.ImagesRepository


class ListImagesUC(
    private val imagesRepository: ImagesRepository
) {
    suspend fun getImagesList() = imagesRepository.getImagesList()
}