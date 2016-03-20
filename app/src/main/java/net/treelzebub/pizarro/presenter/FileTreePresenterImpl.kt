package net.treelzebub.pizarro.presenter

import net.treelzebub.pizarro.explorer.model.FileTreeModelImpl
import net.treelzebub.pizarro.explorer.model.FileMetadata

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
}
