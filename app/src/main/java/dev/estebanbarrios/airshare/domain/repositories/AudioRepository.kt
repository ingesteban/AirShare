package dev.estebanbarrios.airshare.domain.repositories

import dev.estebanbarrios.airshare.data.entities.models.Audio

interface AudioRepository {

    fun getAudioList(): List<Audio>
}