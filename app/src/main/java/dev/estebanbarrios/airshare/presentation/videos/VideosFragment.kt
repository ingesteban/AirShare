package dev.estebanbarrios.airshare.presentation.videos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.presentation.send.interfaces.VideosInterface
import kotlinx.android.synthetic.main.fragment_videos.view.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class VideosFragment : Fragment() {

    var sendFragment: VideosInterface? = null
    private val videosAdapter: VideosAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_videos, container, false)

        view.videosRV.layoutManager = LinearLayoutManager(context)
        view.videosRV.adapter = videosAdapter

        sendFragment?.getVideos()
        // Inflate the layout for this fragment
        return view
    }

    fun setVideos() {
        sendFragment?.videosList?.let {
            if (it.isNotEmpty()) {
                videosAdapter.setItems(it)
            } else {

            }
        }
    }

}
