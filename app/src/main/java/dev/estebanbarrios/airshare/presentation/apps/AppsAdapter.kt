package dev.estebanbarrios.airshare.presentation.apps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.App
import kotlinx.android.synthetic.main.item_app.view.*

class AppsAdapter : RecyclerView.Adapter<AppsAdapter.AudioViewHolder>() {

    private var apps: List<App> = emptyList()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_app, parent, false)
        context = parent.context
        return AudioViewHolder(view)
    }

    override fun getItemCount() = apps.size

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val app = apps[position]

        holder.appContainer.setOnClickListener {
            app.isSelected = !app.isSelected
            notifyDataSetChanged()
        }

        holder.name.text = app.name
        context?.let {
            Glide.with(it)
                .load(app.icon)
                .into(holder.appIcon)
        }

        if (app.isSelected) {
            holder.checkbox.setImageResource(R.drawable.ic_selected_check)
        } else {
            holder.checkbox.setImageResource(R.drawable.ic_unselected_check)
        }

        //holder.weight.text =  app.size.toWeightMeasure()
    }

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val appContainer: ConstraintLayout = itemView.appContainer
        val checkbox: ImageView = itemView.checkbox
        val name: TextView = itemView.name
        val appIcon: ImageView = itemView.appIcon
       //val weight: TextView = itemView.weight
    }

    fun setItems(items: List<App>) {
        apps = items
        notifyDataSetChanged()
    }

}