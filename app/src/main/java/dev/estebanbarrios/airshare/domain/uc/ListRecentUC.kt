package dev.estebanbarrios.airshare.domain.uc

import dev.estebanbarrios.airshare.domain.repositories.RecentRepository


class ListRecentUC(
    private val recentRepository: RecentRepository
) {
    suspend fun getRecentFiles() = recentRepository.getRecentFiles()
}