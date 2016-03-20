package net.treelzebub.pizarro.presenter

import android.content.Context
import android.net.Uri
import net.treelzebub.pizarro.explorer.model.FileTreeModelImpl
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreePresenterImpl(override var view: FileTreeView?) : FileTreePresenter {

    private val model: FileTreeModelImpl = FileTreeModelImpl()
    private var metadataItems: List<FileMetadata> = listOf()

    override fun create() {
        if (metadataItems.isEmpty()) {
            metadataItems = model.ls(null)
        }
        view?.setFileTree(metadataItems)
    }

    override fun changeDirOrOpen(c: Context, uri: Uri) {
        val file = File(uri.path)
        if (file.isDirectory) {
            view?.setFileTree(model.ls(file))
        } else {
            model.exec(c, uri)
        }
    }
}
