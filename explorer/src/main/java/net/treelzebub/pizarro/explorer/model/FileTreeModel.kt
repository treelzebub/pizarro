package net.treelzebub.pizarro.explorer.model

import android.os.Environment
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
object FileTreeModel {

    private val rootDir: File get() = Environment.getExternalStorageDirectory()
    private val rootUriString = rootDir.toURI().toString()

    fun ls(file: File = rootDir): List<Metadata> {
        return file.listFiles().map { Metadata(it) }.sortedBy { it.name.first().toInt() }
    }
}
