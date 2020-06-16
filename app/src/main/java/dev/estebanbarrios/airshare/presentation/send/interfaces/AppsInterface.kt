package dev.estebanbarrios.airshare.presentation.send.interfaces

import dev.estebanbarrios.airshare.data.entities.models.App

interface AppsInterface {
    var appsList: List<App>?
    fun getApps()
    fun addAppsToSend(appsList: List<App>)

}