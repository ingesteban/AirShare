package dev.estebanbarrios.airshare.presentation.audio

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.presentation.send.interfaces.AudiosInterface
import kotlinx.android.synthetic.main.fragment_audio.view.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class AudioFragment : Fragment() {

    var sendFragment: AudiosInterface? = null
    private val audioAdapter: AudioAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_audio, container, false)

        view.audioRV.layoutManager = LinearLayoutManager(context)
        view.audioRV.adapter = audioAdapter

        sendFragment?.getAudios()

        // Inflate the layout for this fragment
        return view
    }


    fun setAudios() {
        sendFragment?.audiosList?.let {
            if (it.isNotEmpty()) {
                audioAdapter.setItems(it)
            } else {

            }
        }
    }

}
