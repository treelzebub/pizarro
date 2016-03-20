package net.treelzebub.pizarro.presenter

import android.content.Context
import android.net.Uri
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import net.treelzebub.pizarro.explorer.model.FileTreeModel
import net.treelzebub.pizarro.explorer.model.FileTreeModelImpl
import java.io.File
import java.net.URI

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreePresenterImpl(override var view: FileTreeView?) : FileTreePresenter {

    private val model: FileTreeModel = FileTreeModelImpl()
    private var metadataItems: List<FileMetadata> = listOf()

    override fun create() {
        if (metadataItems.isEmpty()) {
            metadataItems = model.ls(null)
        }
        updateFileTree()
    }

    override fun reload() {
        metadataItems = model.reload()
        updateFileTree()
    }

    override fun changeDirOrOpen(c: Context, data: FileMetadata) {
        val newFile = File(URI(data.uri.toString()))
        if (newFile.isDirectory) {
            metadataItems = model.cd(data.parent, newFile)
            updateFileTree()
        } else {
            model.exec(c, Uri.fromFile(newFile))
        }
    }

    override fun mkDir(name: String): Boolean {
        return model.mkDir(name)
    }

    override fun rm(data: FileMetadata): Boolean {
        return if (model.rm(data.file)) {
            metadataItems = model.reload()
            updateFileTree()
            true
        } else false
    }

    override fun canGoBack(): Boolean {
        return model.canGoBack()
    }

    override fun onBack() {
        val oneUp = model.back()
        if (oneUp != null) {
            metadataItems = oneUp
            updateFileTree()
        }
    }

    private fun updateFileTree() {
        view?.setFileTree(metadataItems)
    }
}
