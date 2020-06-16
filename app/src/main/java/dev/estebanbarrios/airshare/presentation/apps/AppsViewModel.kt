package dev.estebanbarrios.airshare.presentation.apps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.estebanbarrios.airshare.data.entities.models.App
import dev.estebanbarrios.airshare.data.entities.models.Audio
import dev.estebanbarrios.airshare.data.entities.models.Video
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.ModelResponse
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.domain.uc.ListAppsUC
import dev.estebanbarrios.airshare.domain.uc.ListAudioUC
import dev.estebanbarrios.airshare.domain.uc.ListVideosUC

class AppsViewModel(
    private val listAppsUC: ListAppsUC,
    val appsData: MutableLiveData<ModelResponse<List<App>>>
) : ViewModel() {


}