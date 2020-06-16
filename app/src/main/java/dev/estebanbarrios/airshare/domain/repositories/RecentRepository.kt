package dev.estebanbarrios.airshare.domain.repositories

import dev.estebanbarrios.airshare.data.entities.models.FileRecentGrouped

interface RecentRepository {

    suspend fun getRecentFiles(): List<FileRecentGrouped>

}