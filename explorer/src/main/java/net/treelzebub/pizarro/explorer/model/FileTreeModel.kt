package net.treelzebub.pizarro.explorer.model

import android.content.Context
import android.net.Uri
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File
import java.util.*

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeModel {

    val stack: Stack<File>

    fun ls(dir: File?): List<FileMetadata>
    fun reload(): List<FileMetadata>
    fun cd(oldDir: File, newDir: File): List<FileMetadata>
    fun exec(c: Context, uri: Uri)
    fun mkDir(name: String): Boolean
    fun canGoBack(): Boolean
    fun back(): List<FileMetadata>?
}
