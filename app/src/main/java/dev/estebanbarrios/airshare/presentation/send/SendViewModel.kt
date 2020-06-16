package dev.estebanbarrios.airshare.presentation.send

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.estebanbarrios.airshare.data.entities.models.*
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.Loading
import dev.estebanbarrios.airshare.data.entities.utils.ModelResponse
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.domain.uc.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SendViewModel(
    private val listImagesUC: ListImagesUC,
    private val listVideosUC: ListVideosUC,
    private val listAppsUC: ListAppsUC,
    private val listAudioUC: ListAudioUC,
    private val listRecentUC: ListRecentUC,
    val imagesData: MutableLiveData<ModelResponse<List<ImagesGrouped>>>,
    val videosData: MutableLiveData<ModelResponse<List<VideoGrouped>>>,
    val appsData: MutableLiveData<ModelResponse<List<App>>>,
    val audiosData: MutableLiveData<ModelResponse<List<Audio>>>,
    val recentData: MutableLiveData<ModelResponse<List<FileRecentGrouped>>>,
    val job: Job
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = job

    fun getVideos() {
        launch {
            videosData.postValue(Loading(true))
            val videosList = listVideosUC.getVideosList()
            if (videosList.isEmpty()) {
                videosData.postValue(EmptyResponse())
            } else {
                videosData.postValue(SuccessResponse(videosList))
            }
        }
    }

    fun getImages() {
        launch {
            imagesData.postValue(Loading(true))
            val imagesList = listImagesUC.getImagesList()
            if (imagesList.isEmpty()) {
                imagesData.postValue(EmptyResponse())
            } else {
                imagesData.postValue(SuccessResponse(imagesList))
            }
        }
    }

    fun getApps() {
        launch {
            appsData.postValue(Loading(true))
            val appsList = listAppsUC.getAppsList()
            if (appsList.isEmpty()) {
                appsData.postValue(EmptyResponse())
            } else {
                appsData.postValue(SuccessResponse(appsList))

            }
        }
    }

    fun getAudios() {
        launch {
            audiosData.postValue(Loading(true))
            val audioList = listAudioUC.getAudioList()
            if (audioList.isEmpty()) {
                audiosData.postValue(EmptyResponse())
            } else {
                audiosData.postValue(SuccessResponse(audioList))
            }
        }
    }

    fun getRecentFiles() {
        launch {
            recentData.postValue(Loading(true))
            val recentFiles = listRecentUC.getRecentFiles()
            if (recentFiles.isEmpty()) {
                recentData.postValue(EmptyResponse())
            } else {
                recentData.postValue(SuccessResponse(recentFiles))
            }
        }
    }

}