package dev.estebanbarrios.airshare.domain.uc

import dev.estebanbarrios.airshare.domain.repositories.AudioRepository


class ListAudioUC(
    private val audiosRepository: AudioRepository
) {
    fun getAudioList() = audiosRepository.getAudioList()
}