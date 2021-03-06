package net.treelzebub.pizarro.explorer.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import net.treelzebub.kapsule.extensions.TAG
import net.treelzebub.kapsule.extensions.safePeek
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File
import java.util.*

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeModelImpl : FileTreeModel {

    private val rootDir: File    get() = Environment.getExternalStorageDirectory()
    private var currentDir: File = rootDir

    override val stack: Stack<File> = Stack()

    override fun ls(dir: File?): List<FileMetadata> {
        val safeDir = dir ?: rootDir
        currentDir = safeDir
        val parentDirList = if (currentDir != rootDir) {
            arrayListOf(FileMetadata(currentDir.parentFile, true))
        } else {
            arrayListOf()
        }
        return parentDirList +
                safeDir.listFiles()
                        .sortedWith(FileComparator())
                        .map { FileMetadata(it) }
    }

    override fun reload(): List<FileMetadata> {
        return ls(currentDir)
    }

    override fun cd(oldDir: File, newDir: File): List<FileMetadata> {
        stack.push(oldDir)
        return ls(newDir)
    }

    override fun exec(c: Context, uri: Uri) {
        val file = File(uri.path)
        val mimeType = MimeTypeMap.getSingleton()
                .getMimeTypeFromExtension(file.extension)
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            type = mimeType ?: "*/*"
            setDataAndType(uri, type)
        }
        c.startActivity(intent)
    }

    override fun mkDir(name: String): Boolean {
        return File(currentDir.path + "/$name").mkdir()
    }

    override fun rm(file: File): Boolean {
        val absFile = File(file.toURI())
        if (!absFile.isDirectory) {
            return absFile.delete()
        }
        Log.d(TAG, if (!absFile.exists()) "${file.absolutePath} deleted" else "${file.absolutePath} not deleted")
        return false
    }

    override fun canGoBack(): Boolean {
        return stack.safePeek() != null
    }

    override fun back(): List<FileMetadata>? {
        return if (stack.isNotEmpty()) {
            ls(stack.pop())
        } else null
    }

    private inner class FileComparator : Comparator<File> {
        override fun compare(o1: File, o2: File): Int {
            if (o1.isDirectory && !o2.isDirectory) {
                // Directory before non-directory
                return -1
            } else if (!o1.isDirectory && o2.isDirectory) {
                // Non-directory after directory
                return 1
            } else {
                // Alphabetic order otherwise
                return o1.compareTo(o2)
            }
        }
    }
}
