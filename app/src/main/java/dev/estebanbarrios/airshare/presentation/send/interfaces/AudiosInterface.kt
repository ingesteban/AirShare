package dev.estebanbarrios.airshare.presentation.send.interfaces

import dev.estebanbarrios.airshare.data.entities.models.Audio

interface AudiosInterface {
    var audiosList: List<Audio>?
    fun getAudios()
    fun addAudiosToSend(audiosList: List<Audio>)

}