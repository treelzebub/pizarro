package net.treelzebub.pizarro.view

import net.treelzebub.pizarro.explorer.entities.FileMetadata

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeView {

    fun setFileTree(treeItems: List<FileMetadata>?)
}
