package dev.estebanbarrios.airshare.presentation.audio

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.Audio
import dev.estebanbarrios.airshare.presentation.extensions.toLongDate
import dev.estebanbarrios.airshare.presentation.extensions.toMins
import kotlinx.android.synthetic.main.item_audio.view.*

class AudioAdapter : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    private var audios: List<Audio> = emptyList()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_audio, parent, false)
        context = parent.context
        return AudioViewHolder(view)
    }

    override fun getItemCount() = audios.size

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audio = audios[position]

        holder.audioContainer.setOnClickListener {
            audio.isSelected = !audio.isSelected
            notifyDataSetChanged()
        }

        holder.name.text = audio.name
        holder.date.text =  audio.date.toLongDate()
        holder.duration.text = audio.duration.toMins()

        if (audio.isSelected) {
            holder.checkbox.setImageResource(R.drawable.ic_selected_check)
        } else {
            holder.checkbox.setImageResource(R.drawable.ic_unselected_check)
        }
    }

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val audioContainer: ConstraintLayout = itemView.audioContainer
        val checkbox: ImageView = itemView.checkbox
        val name: TextView = itemView.name
        val date: TextView = itemView.date
        val duration: TextView = itemView.duration
    }

    fun setItems(items: List<Audio>) {
        audios = items
        notifyDataSetChanged()
    }

}