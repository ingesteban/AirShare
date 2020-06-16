package dev.estebanbarrios.airshare.presentation.recent

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.FileRecentGrouped
import dev.estebanbarrios.airshare.data.entities.models.FileRecentType
import dev.estebanbarrios.airshare.data.entities.models.TypeAdapter
import dev.estebanbarrios.airshare.presentation.extensions.toLongDate
import dev.estebanbarrios.airshare.presentation.extensions.toMinsSecs
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_recent_audio.view.*
import kotlinx.android.synthetic.main.item_recent_photo.view.*
import kotlinx.android.synthetic.main.item_recent_video.view.*
import kotlinx.android.synthetic.main.item_header.view.checkbox as checkboxHeader


class RecentAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val IMAGES = 0
        private const val AUDIO = 1
        private const val VIDEOS = 2
        private const val HEADER = 3
    }

    private var filesRecent: List<FileRecentGrouped> = emptyList()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            IMAGES -> {
                PhotosViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_recent_photo,
                        parent,
                        false
                    )
                )
            }
            AUDIO -> {
                AudioViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_recent_audio,
                        parent,
                        false
                    )
                )
            }
            VIDEOS -> {
                VideosViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_recent_video,
                        parent,
                        false
                    )
                )
            }
            HEADER -> {
                HeaderViewHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_header,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun getItemCount() = filesRecent.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val fileGrouped = filesRecent[position]

        when (holder.itemViewType) {
            IMAGES -> {
                holder as PhotosViewHolder
                holder.photoFileContainer.setOnClickListener {
                    fileGrouped.isSelected = !fileGrouped.isSelected
                    notifyDataSetChanged()
                }
                context?.let {
                    Glide.with(it)
                        .load(fileGrouped.fileRecent?.images?.uri)
                        .into(holder.photoFileThumbnail)
                }
                if (fileGrouped.isSelected) {
                    holder.photoFileCheckbox.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.photoFileCheckbox.setImageResource(R.drawable.ic_unselected_check)
                }
            }
            AUDIO -> {
                holder as AudioViewHolder

                holder.audioFileContainer.setOnClickListener {
                    fileGrouped.isSelected = !fileGrouped.isSelected
                    notifyDataSetChanged()
                }
                if (fileGrouped.isSelected) {
                    holder.audioFileCheckbox.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.audioFileCheckbox.setImageResource(R.drawable.ic_unselected_check)
                }
            }
            VIDEOS -> {
                holder as VideosViewHolder
                holder.videoFileContainer.setOnClickListener {
                    fileGrouped.isSelected = !fileGrouped.isSelected
                    notifyDataSetChanged()
                    onClickRecent.onClick(fileGrouped.fileRecent)
                }
                context?.let {
                    Glide.with(it)
                        .load(fileGrouped.fileRecent.video?.uri)
                        .into(holder.videoFileThumbnail)
                }
                holder.videoFileSize.text = fileGrouped.fileRecent.video?.duration?.toMinsSecs()
                if (fileGrouped.isSelected) {
                    holder.videoFileCheckbox.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.videoFileCheckbox.setImageResource(R.drawable.ic_unselected_check)
                }
            }
            HEADER -> {
                holder as HeaderViewHolder
                holder.date.text = fileGrouped.date?.toLongDate()
                holder.headerContainer.setOnClickListener {
                    fileGrouped.isSelected = !fileGrouped.isSelected
                    filesRecent.filter { it.grouped == fileGrouped.grouped && it.type == TypeAdapter.ITEM }
                        .map {
                            it.isSelected = fileGrouped.isSelected
                            onClickRecent.onClick(it.fileRecent)
                        }
                    notifyDataSetChanged()
                }
                if (fileGrouped.isSelected) {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_unselected_check)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val file = filesRecent[position]
        return when (file.type) {
            TypeAdapter.HEADER -> HEADER
            TypeAdapter.ITEM -> {
                return when (file.fileRecent!!.type) {
                    FileRecentType.MEDIA_TYPE_IMAGE -> IMAGES
                    FileRecentType.MEDIA_TYPE_AUDIO -> AUDIO
                    FileRecentType.MEDIA_TYPE_VIDEO -> VIDEOS
                    else -> throw IllegalArgumentException("Invalid type of data " + position)
                }
            }
        }
    }


    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoFileContainer: CardView = itemView.photoFileContainer
        val photoFileCheckbox: ImageView = itemView.photoFileCheckbox
        val photoFileThumbnail: ImageView = itemView.photoFileThumbnail
    }

    class VideosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoFileContainer: CardView = itemView.videoFileContainer
        val videoFileCheckbox: ImageView = itemView.videoFileCheckbox
        val videoFileThumbnail: ImageView = itemView.videoFileThumbnail
        val videoFileSize: TextView = itemView.videoFileSize
    }

    class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val audioFileContainer: CardView = itemView.audioFileContainer
        val audioFileCheckbox: ImageView = itemView.audioFileCheckbox
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.date
        val headerContainer: CardView = itemView.headerContainer
        val checkboxHeader: ImageView = itemView.checkboxHeader
    }

    fun setItems(items: List<FileRecentGrouped>) {
        filesRecent = items
        notifyDataSetChanged()
    }

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (filesRecent[position].type == TypeAdapter.HEADER) 4 else 1
        }
    }

    lateinit var onClickRecent : OnClickRecent


}