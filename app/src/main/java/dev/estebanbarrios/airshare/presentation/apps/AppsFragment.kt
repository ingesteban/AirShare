package dev.estebanbarrios.airshare.presentation.apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.presentation.send.interfaces.AppsInterface
import kotlinx.android.synthetic.main.fragment_apps.view.*
import org.koin.android.ext.android.inject


/**
 * A simple [Fragment] subclass.
 */
class AppsFragment : Fragment() {

    var sendFragment: AppsInterface? = null
    private val appsAdapter: AppsAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_apps, container, false)

        view.appsRV.layoutManager = LinearLayoutManager(context)
        view.appsRV.adapter = appsAdapter

        sendFragment?.getApps()

        // Inflate the layout for this fragment
        return view
    }

    fun setApps() {
        sendFragment?.appsList?.let {
            if (it.isNotEmpty()) {
                appsAdapter.setItems(it)
            } else {

            }
        }
    }



}
