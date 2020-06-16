package dev.estebanbarrios.airshare.presentation.photos

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
import dev.estebanbarrios.airshare.data.entities.models.ImagesGrouped
import dev.estebanbarrios.airshare.data.entities.models.TypeAdapter
import dev.estebanbarrios.airshare.presentation.extensions.toLongDate
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_photo.view.*
import kotlinx.android.synthetic.main.item_photo.view.checkbox
import kotlinx.android.synthetic.main.item_header.view.checkbox as checkboxHeader

class PhotosAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var photos: List<ImagesGrouped> = emptyList()
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
            else -> PhotosViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.item_photo,
                    parent,
                    false
                )
            )
        }

    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                holder as HeaderViewHolder
                val imageGrouped = photos[position]
                holder.date.text = imageGrouped.date?.toLongDate()
                holder.headerContainer.setOnClickListener {
                    imageGrouped.isSelected = !imageGrouped.isSelected
                    photos.filter { it.grouped == imageGrouped.grouped && it.type == TypeAdapter.ITEM }
                        .map { it.isSelected = imageGrouped.isSelected }
                    notifyDataSetChanged()
                }
                if (imageGrouped.isSelected) {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_selected_check)
                } else {
                    holder.checkboxHeader.setImageResource(R.drawable.ic_unselected_check)
                }
            }
            else -> {
                holder as PhotosViewHolder
                val imageGrouped = photos[position]
                imageGrouped.image?.let { photo ->
                    holder.photoContainer.setOnClickListener {
                        imageGrouped.isSelected = !imageGrouped.isSelected
                        notifyDataSetChanged()
                    }
                    context?.let {
                        Glide.with(it)
                            .load(photo.uri)
                            .into(holder.thumbnail)
                    }
                    if (imageGrouped.isSelected) {
                        holder.checkbox.setImageResource(R.drawable.ic_selected_check)
                    } else {
                        holder.checkbox.setImageResource(R.drawable.ic_unselected_check)
                    }
                }


            }
        }

    }

    class PhotosViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoContainer: CardView = itemView.photoContainer
        val checkbox: ImageView = itemView.checkbox
        val thumbnail: ImageView = itemView.thumbnail
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date: TextView = itemView.date
        val headerContainer: CardView = itemView.headerContainer
        val checkboxHeader: ImageView = itemView.checkboxHeader
    }

    override fun getItemViewType(position: Int) = when (photos[position].type) {
        TypeAdapter.HEADER -> 0
        TypeAdapter.ITEM -> 1
    }

    fun setItems(items: List<ImagesGrouped>) {
        photos = items
        notifyDataSetChanged()
    }

    val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (photos[position].type == TypeAdapter.HEADER) 4 else 1
        }
    }

}