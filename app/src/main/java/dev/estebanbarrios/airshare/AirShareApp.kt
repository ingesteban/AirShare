package dev.estebanbarrios.airshare

import android.app.Application
import dev.estebanbarrios.airshare.presentation.di.repositoriesModule
import dev.estebanbarrios.airshare.presentation.di.useCasesModule
import dev.estebanbarrios.airshare.presentation.di.utilsModule
import dev.estebanbarrios.airshare.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class AirShareApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidLogger()
            androidContext(this@AirShareApp)
            modules(listOf(viewModelModule, useCasesModule, repositoriesModule, utilsModule))
        }
    }

    init {
        instance = this
    }

    companion object {
        private var instance: AirShareApp? = null
        fun applicationContext(): AirShareApp {
            return instance!!
        }
    }

}