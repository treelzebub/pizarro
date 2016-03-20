package net.treelzebub.pizarro.explorer.model

import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeModel {

    fun ls(dir: File?): List<FileMetadata>
}
