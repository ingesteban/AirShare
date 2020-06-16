package dev.estebanbarrios.airshare.presentation.recent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.FileRecent
import dev.estebanbarrios.airshare.data.entities.utils.EmptyResponse
import dev.estebanbarrios.airshare.data.entities.utils.SuccessResponse
import dev.estebanbarrios.airshare.presentation.send.interfaces.RecentInterface
import kotlinx.android.synthetic.main.fragment_recent.view.*
import org.koin.android.ext.android.inject

/**
 * A simple [Fragment] subclass.
 */
class RecentFragment : Fragment(), OnClickRecent {
    var sendFragment: RecentInterface? = null
    private val recentAdapter: RecentAdapter by inject()

    // Container for information about each video.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recent, container, false)

        recentAdapter.onClickRecent = this
        var mLayoutManager = GridLayoutManager(context, 4)
        mLayoutManager.spanSizeLookup = recentAdapter.spanSizeLookup
        view.recentRV.layoutManager = mLayoutManager
        view.recentRV.adapter = recentAdapter

        sendFragment?.getRecent()

        // Inflate the layout for this fragment
        return view
    }

    fun setRecentFiles() {
        sendFragment?.recentList?.let {
            if (it.isNotEmpty()) {
                recentAdapter.setItems(it)
            } else {

            }
        }
    }

    override fun onClick(fileRecent: FileRecent) {
        TODO("Not yet implemented")
    }
}
