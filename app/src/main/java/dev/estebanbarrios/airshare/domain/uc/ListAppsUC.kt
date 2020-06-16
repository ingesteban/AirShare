package dev.estebanbarrios.airshare.domain.uc

import dev.estebanbarrios.airshare.domain.repositories.AppsRepository


class ListAppsUC(
    private val appsRepository: AppsRepository
) {
    suspend fun getAppsList() = appsRepository.getApps()
}