package dev.estebanbarrios.airshare.presentation.files

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.estebanbarrios.airshare.R
import dev.estebanbarrios.airshare.data.entities.models.FileType
import dev.estebanbarrios.airshare.presentation.extensions.checkFileType
import dev.estebanbarrios.airshare.presentation.extensions.toInvisible
import dev.estebanbarrios.airshare.presentation.extensions.toVisible
import kotlinx.android.synthetic.main.fragment_files.view.*
import org.koin.android.ext.android.inject
import java.io.File
import java.io.FileFilter

class FilesFragment : Fragment(), OnFileClickListener {

    private val filesAdapter: FilesAdapter by inject()

    private lateinit var currentDirectoryPath: String
    private lateinit var previousDirectoryPath: String
    private val fileType: FileType = FileType.ALL
    private lateinit var backButton: ImageView
    private lateinit var textStorage: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filesAdapter.onFileClickListener = this

        val view = inflater.inflate(R.layout.fragment_files, container, false)
        backButton = view.backImage
        textStorage = view.textStorage

        view.filesRV.layoutManager = LinearLayoutManager(context)
        view.filesRV.adapter = filesAdapter

        currentDirectoryPath = Environment.getExternalStorageDirectory().path
        textStorage.text = File(currentDirectoryPath).name
        loadItems()

        backButton.setOnClickListener {
            currentDirectoryPath = previousDirectoryPath
            File(currentDirectoryPath).parentFile?.let {
                previousDirectoryPath = it.path
            } ?: run {
                previousDirectoryPath = Environment.getExternalStorageDirectory().path
            }


            textStorage.text = File(currentDirectoryPath).name
            loadItems()
            if (File(currentDirectoryPath).name == Environment.getExternalStorageDirectory().name) {
                backButton.toInvisible()
            }
        }
        backButton.toInvisible()

        return view
    }

    override fun onClick(file: File) {
        if (file.isDirectory) {
            previousDirectoryPath = currentDirectoryPath
            currentDirectoryPath = file.path
            textStorage.text = File(currentDirectoryPath).name
            loadItems()
            if (File(currentDirectoryPath).name != File(Environment.getExternalStorageDirectory().path).name) {
                backButton.toVisible()
            }
        }
    }

    private fun loadItems() {
        val files = File(currentDirectoryPath).listFiles(FileFilter { file ->
            if (file.canRead()) {
                if (file.isFile) {
                    if (file.extension.checkFileType(fileType)) {
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
        files?.sortWith(compareBy({ !it.isDirectory }, { it.name.toLowerCase() }))

        filesAdapter.setItems(files.toList())
    }

}
