package net.treelzebub.pizarro.explorer.model

import android.os.Environment
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeModelImpl : FileTreeModel {

    companion object {
        private val rootDir: File get() = Environment.getExternalStorageDirectory()
        val rootPath = rootDir.path
    }

    override fun ls(dir: File?): List<FileMetadata> {
        return (dir ?: rootDir)
                .listFiles()
                .map { FileMetadata(it) }
                .sortedBy { it.name.first().toInt() }
    }
}
