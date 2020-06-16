package dev.estebanbarrios.airshare.presentation.videos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.TypeAdapter
import dev.estebanbarrios.airshare.data.entities.models.VideoGrouped
import dev.estebanbarrios.airshare.presentation.extensions.toLongDate
import dev.estebanbarrios.airshare.presentation.extensions.toMB
import dev.estebanbarrios.airshare.presentation.extensions.toMinsSecs
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_video.view.*
import kotlinx.android.synthetic.main.item_video.view.checkbox
import kotlinx.android.synthetic.main.item_header.view.checkbox as checkboxHeader

class VideosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var videos: List<VideoGrouped> = emptyList()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context

        return when (viewType) {
            0 -> HeaderViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_header,
                    parent,
                    false
                )
            )
            else -> VideosViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_video,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemCount() = videos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> {
                holder as HeaderViewHolder
                val videoGrouped = videos[position]
                holder.date.text = videoGrouped.date?.toLongDate()
                holder.headerContainer.setOnClickListener {
                    videoGrouped.isSelected = !videoGrouped.isSelected
                    videos.filter { it.grouped == videoGrouped.grouped && it.type == TypeAdapter.ITEM }
                        .map { it.isSelected = videoGrouped.isSelected }
                    notifyDataSetChanged()
                }
                if (videoGrouped.isSelected) {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_unselected_check)
                }
            }
            else -> {
                holder as VideosViewHolder
                val videoGrouped = videos[position]
                videoGrouped.video?.let { video ->
                    holder.videoContainer.setOnClickListener {
                        videoGrouped.isSelected = !videoGrouped.isSelected
                        notifyDataSetChanged()
                    }
                    context?.let {
                        Glide.with(it)
                            .load(video.uri)
                            .into(holder.thumbnail)
                    }
                    holder.size.text = video.size.toMB()
                    holder.name.text = video.name
                    holder.duration.text = video.duration.toMinsSecs()
                    if (videoGrouped.isSelected) {
                        holder.checkbox.setImageResource(R.drawable.ic_selected_check)
                    } else {
                        holder.checkbox.setImageResource(R.drawable.ic_unselected_check)
                    }
                }


            }
        }


    }

    class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoContainer: ConstraintLayout = itemView.videoContainer
        val checkbox: ImageView = itemView.checkbox
        val thumbnail: ImageView = itemView.thumbnail
        val duration: TextView = itemView.duration
        val name: TextView = itemView.name
        val size: TextView = itemView.size
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.date
        val headerContainer: CardView = itemView.headerContainer
        val checkboxHeader: ImageView = itemView.checkboxHeader
    }

    override fun getItemViewType(position: Int) = when (videos[position].type) {
        TypeAdapter.HEADER -> 0
        TypeAdapter.ITEM -> 1
    }

    fun setItems(items: List<VideoGrouped>) {
        videos = items
        notifyDataSetChanged()
    }
}