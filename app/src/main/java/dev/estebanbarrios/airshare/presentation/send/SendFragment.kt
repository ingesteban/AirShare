package dev.estebanbarrios.airshare.presentation.send

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayoutMediator
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.*
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.Loading
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.presentation.apps.AppsFragment
import dev.estebanbarrios.airshare.presentation.audio.AudioFragment
import dev.estebanbarrios.airshare.presentation.extensions.toGone
import dev.estebanbarrios.airshare.presentation.extensions.toVisible
import dev.estebanbarrios.airshare.presentation.files.FilesFragment
import dev.estebanbarrios.airshare.presentation.photos.PhotosFragment
import dev.estebanbarrios.airshare.presentation.recent.RecentFragment
import dev.estebanbarrios.airshare.presentation.send.interfaces.*
import dev.estebanbarrios.airshare.presentation.videos.VideosFragment
import kotlinx.android.synthetic.main.fragment_send.view.*
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 */
class SendFragment : Fragment(),
    VideosInterface,
    PhotosInterface,
    AppsInterface,
    AudiosInterface,
    RecentInterface {

    private val sendViewModel: SendViewModel by viewModel()

    //Fragments
    private var recentFragment = RecentFragment()
    private var photosFragment = PhotosFragment()
    private var videosFragment = VideosFragment()
    private var audioFragment = AudioFragment()
    private var appsFragment = AppsFragment()
    private var filesFragment = FilesFragment()

    //Progress Bar
    private lateinit var progress: ConstraintLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_send, container, false)
        progress = view.progress
        photosFragment.sendFragment = this
        videosFragment.sendFragment = this
        appsFragment.sendFragment = this
        audioFragment.sendFragment = this
        recentFragment.sendFragment = this

        activity?.let {
            view.viewPager.adapter =
                ViewPagerAdapter(it.supportFragmentManager, lifecycle, getFragmentList())
            TabLayoutMediator(
                view.tabs,
                view.viewPager,
                TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                    tab.text = it.applicationContext.getText(getFragmentTabText()[position])
                }).attach()
        }

        view.sendButton.setOnClickListener {


        }


        initObservers()
        return view
    }


    private fun getFragmentList(): List<Fragment> {

        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(recentFragment)
        fragmentList.add(photosFragment)
        fragmentList.add(videosFragment)
        fragmentList.add(audioFragment)
        fragmentList.add(appsFragment)
        fragmentList.add(filesFragment)

        return fragmentList.toList()
    }

    private fun getFragmentTabText(): List<Int> {
        val textList = ArrayList<Int>()
        textList.add(R.string.tab_recent)
        textList.add(R.string.tab_photos)
        textList.add(R.string.tab_videos)
        textList.add(R.string.tab_audio)
        textList.add(R.string.tab_apps)
        textList.add(R.string.tab_files)

        return textList
    }

    private fun initObservers() {

        //Videos Data
        sendViewModel.videosData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Loading -> {
                    progress.toVisible()
                }
                is EmptyResponse -> {
                    Log.d("", "")
                }
                is SuccessResponse -> {
                    videosList = response.body
                    videosFragment.setVideos()
                    progress.toGone()
                }

            }
        })

        //Images Data
        sendViewModel.imagesData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Loading -> {
                    progress.toVisible()
                }
                is EmptyResponse -> {
                    Log.d("", "")
                }
                is SuccessResponse -> {
                    photosList = response.body
                    photosFragment.setPhotos()
                    progress.toGone()
                }

            }
        })

        //Apps Data
        sendViewModel.appsData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Loading -> {
                    progress.toVisible()
                }
                is EmptyResponse -> {
                    Log.d("", "")
                }
                is SuccessResponse -> {
                    appsList = response.body
                    appsFragment.setApps()
                    progress.toGone()
                }

            }
        })

        //Recent Files Data
        sendViewModel.recentData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Loading -> {
                    progress.toVisible()
                }
                is EmptyResponse -> {
                    Log.d("", "")
                }
                is SuccessResponse -> {
                    recentList = response.body
                    recentFragment.setRecentFiles()
                    progress.toGone()
                }
            }
        })

        //Audios  Data
        sendViewModel.audiosData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Loading -> {
                    progress.toVisible()
                }
                is EmptyResponse -> {
                    Log.d("", "")
                }
                is SuccessResponse -> {
                    audiosList = response.body
                    audioFragment.setAudios()
                    progress.toGone()
                }
            }
        })


    }

    override var videosList: List<VideoGrouped>? = null
    override fun getVideos() {
        videosList?.let {
            videosFragment.setVideos()
        } ?: run {
            sendViewModel.getVideos()
        }
    }
    override fun addVideosToSend(videosList: List<VideoGrouped>) {
        TODO("Not yet implemented")
    }

    override var photosList: List<ImagesGrouped>? = null
    override fun getPhotos() {
        videosList?.let {
            photosFragment.setPhotos()
        } ?: run {
            sendViewModel.getImages()
        }
    }
    override fun addPhotosToSend(photosList: List<ImagesGrouped>) {
        TODO("Not yet implemented")
    }

    override var appsList: List<App>? = null
    override fun getApps() {
        appsList?.let {
            appsFragment.setApps()
        } ?: run {
            sendViewModel.getApps()
        }
    }
    override fun addAppsToSend(appsList: List<App>) {
        TODO("Not yet implemented")
    }

    override var audiosList: List<Audio>? = null
    override fun getAudios() {
        audiosList?.let {
            audioFragment.setAudios()
        } ?: run {
            sendViewModel.getAudios()
        }
    }
    override fun addAudiosToSend(audiosList: List<Audio>) {
        TODO("Not yet implemented")
    }

    override var recentList: List<FileRecentGrouped>? = null
    override fun getRecent() {
        recentList?.let {
            recentFragment.setRecentFiles()
        } ?: run {
            sendViewModel.getRecentFiles()
        }
    }
    override fun addRecentFilesToSend(recentList: List<FileRecentGrouped>) {
        TODO("Not yet implemented")
    }

}
