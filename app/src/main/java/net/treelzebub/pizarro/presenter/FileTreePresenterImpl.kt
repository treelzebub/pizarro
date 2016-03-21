package net.treelzebub.pizarro.presenter

import android.content.Context
import android.net.Uri
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import net.treelzebub.pizarro.explorer.model.FileTreeModel
import net.treelzebub.pizarro.explorer.model.FileTreeModelImpl
import net.treelzebub.pizarro.view.FileTreeView

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreePresenterImpl(override var view: FileTreeView?) : FileTreePresenter {

    private val model: FileTreeModel = FileTreeModelImpl()
    private var metadataItems: List<FileMetadata> = listOf()
        private set(value) {
            field = value
            updateFileTree()
        }

    override fun create() {
        if (metadataItems.isEmpty()) {
            metadataItems = model.ls(null)
        } else {
            reload()
        }
    }

    override fun reload() {
        metadataItems = model.reload()
    }

    override fun changeDirOrOpen(c: Context, data: FileMetadata) {
        val file = data.file
        if (file.isDirectory) {
            metadataItems = model.cd(data.parent, file)
        } else {
            model.exec(c, Uri.fromFile(file))
        }
    }

    override fun mkDir(name: String): Boolean {
        return model.mkDir(name)
    }

    override fun rm(data: FileMetadata): Boolean {
        return if (model.rm(data.file)) {
            reload()
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
        }
    }

    private fun updateFileTree() {
        view?.setFileTree(metadataItems)
    }
}
