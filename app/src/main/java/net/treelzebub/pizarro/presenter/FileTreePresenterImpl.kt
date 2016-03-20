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
        view?.setFileTree(metadataItems)
    }

    override fun changeDirOrOpen(c: Context, data: FileMetadata) {
        val newFile = File(URI(data.uri.toString()))
        if (newFile.isDirectory) {
            view?.setFileTree(model.cd(data.parent, newFile))
        } else {
            model.exec(c, Uri.fromFile(newFile))
        }
    }

    override fun canGoBack(): Boolean {
        return model.canGoBack()
    }

    override fun onBack() {
        view?.setFileTree(model.back())
    }
}
