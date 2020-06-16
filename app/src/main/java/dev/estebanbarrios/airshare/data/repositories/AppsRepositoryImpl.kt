package dev.estebanbarrios.airshare.data.repositories

import android.content.Intent
import android.content.pm.PackageManager
import dev.estebanbarrios.airshare.data.entities.models.App
import dev.estebanbarrios.airshare.domain.repositories.AppsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppsRepositoryImpl(private val packageManager: PackageManager) : AppsRepository {


    override suspend fun getApps(): List<App> {
        val appList = mutableListOf<App>()

        withContext(Dispatchers.IO) {
            val mainIntent = Intent(Intent.ACTION_MAIN, null)
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            val pkgAppsList =
                packageManager.queryIntentActivities(mainIntent, 0)

            for (pkg in pkgAppsList) {
                appList += App(
                    pkg.loadIcon(packageManager),
                    pkg.activityInfo.packageName,
                    pkg.activityInfo.loadLabel(packageManager) as String,
                    123
                )
            }
        }

        return appList.sortedBy { it.name }
    }


}