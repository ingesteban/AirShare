package dev.estebanbarrios.airshare.presentation.di

import androidx.lifecycle.MutableLiveData
import dev.estebanbarrios.airshare.data.repositories.*
import dev.estebanbarrios.airshare.domain.repositories.*
import dev.estebanbarrios.airshare.domain.uc.*
import dev.estebanbarrios.airshare.presentation.apps.AppsAdapter
import dev.estebanbarrios.airshare.presentation.audio.AudioAdapter
import dev.estebanbarrios.airshare.presentation.files.FilesAdapter
import dev.estebanbarrios.airshare.presentation.photos.PhotosAdapter
import dev.estebanbarrios.airshare.presentation.recent.RecentAdapter
import dev.estebanbarrios.airshare.presentation.send.SendViewModel
import dev.estebanbarrios.airshare.presentation.videos.VideosAdapter
import kotlinx.coroutines.Job
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Modulo encargado de instanciar los viewModel
 */
val viewModelModule = module {
    viewModel {
        SendViewModel(
            videosData = MutableLiveData(),
            listVideosUC = get(),
            imagesData = MutableLiveData(),
            listImagesUC = get(),
            appsData = MutableLiveData(),
            listAppsUC = get(),
            audiosData = MutableLiveData(),
            listAudioUC = get (),
            recentData = MutableLiveData(),
            listRecentUC = get(),
            job = Job()
        )
    }
}

/**
 * Modulo encargado de instanciar los casos de uso
 */
val useCasesModule = module {
    factory {
        ListVideosUC(videosRepository = get())
    }
    factory {
        ListAudioUC(audiosRepository = get())
    }
    factory {
        ListImagesUC(imagesRepository = get())
    }
    factory {
        ListRecentUC(recentRepository = get())
    }
    factory {
        ListAppsUC(appsRepository = get())
    }
}

/**
 * Modulo encargado de instanciar los repositorios
 */
val repositoriesModule = module {
    factory {
        VideosRepositoryImpl(contentResolver = androidContext().contentResolver) as VideosRepository
    }
    factory {
        AudioRepositoryImpl(contentResolver = androidContext().contentResolver) as AudioRepository
    }
    factory {
        ImagesRepositoryImpl(contentResolver = androidContext().contentResolver) as ImagesRepository
    }
    factory {
        RecentRepositoryImpl(contentResolver = androidContext().contentResolver) as RecentRepository
    }
    factory {
        AppsRepositoryImpl(packageManager = androidContext().packageManager) as AppsRepository
    }
}

/**
 * Modulo encargado de instanciar las utilidades
 */
val utilsModule = module {
    factory { VideosAdapter() }
    factory { AudioAdapter() }
    factory { PhotosAdapter() }
    factory { RecentAdapter() }
    factory { AppsAdapter() }
    factory { FilesAdapter() }
}
