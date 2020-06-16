package dev.estebanbarrios.airshare.presentation.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.presentation.send.interfaces.PhotosInterface
import kotlinx.android.synthetic.main.fragment_photos.view.*
import org.koin.android.ext.android.inject


class PhotosFragment : Fragment() {

    var sendFragment: PhotosInterface? = null
    private val photosAdapter: PhotosAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        var mLayoutManager = GridLayoutManager(context, 4)
        mLayoutManager.spanSizeLookup = photosAdapter.spanSizeLookup
        view.photosRV.layoutManager = mLayoutManager
        view.photosRV.adapter = photosAdapter

        sendFragment?.getPhotos()

        // Inflate the layout for this fragment
        return view
    }


    fun setPhotos() {
        sendFragment?.photosList?.let {
            if (it.isNotEmpty()) {
                photosAdapter.setItems(it)
            } else {

            }
        }

    }

}
