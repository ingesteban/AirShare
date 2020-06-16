package dev.estebanbarrios.airshare.presentation.files

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
import dev.estebanbarrios.airshare.data.entities.models.FileType
import dev.estebanbarrios.airshare.presentation.extensions.checkExtensionType
import dev.estebanbarrios.airshare.presentation.extensions.checkFileType
import kotlinx.android.synthetic.main.item_file.view.*
import java.io.File
import java.io.FileFilter

class FilesAdapter : RecyclerView.Adapter<FilesAdapter.FilesViewHolder>() {

    private var files: List<File> = emptyList()
    private var context: Context? = null
    lateinit var onFileClickListener: OnFileClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file, parent, false)
        context = parent.context
        return FilesViewHolder(view)
    }

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val file = files[position]

        holder.fileContainer.setOnClickListener {
            onFileClickListener.onClick(file)
        }

        holder.checkbox.setOnClickListener {

        }

        holder.name.text = file.name

        val icon = if (file.isDirectory) {
            R.drawable.ic_folder
        } else {
            when (file.extension.checkExtensionType()) {
                FileType.APK -> R.drawable.button_primary_dark_border
                FileType.AUDIO -> R.drawable.ic_audio
                FileType.CODE -> R.drawable.ic_code
                FileType.DATABASE -> R.drawable.ic_db
                FileType.DOCUMENTS -> R.drawable.ic_doc
                FileType.IMAGE -> 0
                FileType.VIDEO -> 0
                else -> R.drawable.ic_file
            }
        }
        context?.let {
            if (icon == 0) {
                Glide.with(it)
                    .load(file)
                    .into(holder.fileIcon)

            } else {
                Glide.with(it)
                    .load(icon)
                    .into(holder.fileIcon)
            }
        }

        if (file.isDirectory) {
            val items = getCountItems(file.path)
            holder.numberItems.text = context?.resources?.getQuantityString(
                R.plurals.numberOfItems,
                items,
                items
            )
        } else {
            holder.numberItems.text = String()
        }


    }

    class FilesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fileContainer: ConstraintLayout = itemView.fileContainer
        val checkbox: ImageView = itemView.checkbox
        val name: TextView = itemView.name
        val fileIcon: ImageView = itemView.fileIcon
        val numberItems: TextView = itemView.numberItems
    }

    fun setItems(items: List<File>) {
        files = items
        notifyDataSetChanged()
    }

    private fun getCountItems(currentDirectoryPath: String): Int {
        val files = File(currentDirectoryPath).listFiles(FileFilter { file ->
            if (file.canRead()) {
                if (file.isFile) {
                    if (file.extension.checkFileType(FileType.ALL)) {
                        if (!file.name.startsWith(".")) {
                            return@FileFilter true
                        }
                    }
                }
                if (file.isDirectory && !file.name.startsWith(".")) {
                    return@FileFilter file.isDirectory
                }
            }
            false
        })

        files?.let {
            return files.size
        }

        return 0
    }


}