package dev.estebanbarrios.airshare.presentation.send.interfaces

import dev.estebanbarrios.airshare.data.entities.models.ImagesGrouped

interface PhotosInterface {
    var photosList: List<ImagesGrouped>?
    fun getPhotos()
    fun addPhotosToSend(photosList: List<ImagesGrouped>)

}