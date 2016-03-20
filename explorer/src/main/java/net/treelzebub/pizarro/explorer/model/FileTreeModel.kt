package net.treelzebub.pizarro.explorer.model

import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeModel {

    fun ls(dir: File?): List<FileMetadata>
}
