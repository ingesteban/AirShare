package dev.estebanbarrios.airshare.domain.repositories

import dev.estebanbarrios.airshare.data.entities.models.App


interface AppsRepository {

    suspend fun getApps(): List<App>
}