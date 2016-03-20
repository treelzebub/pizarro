package net.treelzebub.pizarro.explorer.model

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeModelImpl : FileTreeModel {

    private val rootDir: File get() = Environment.getExternalStorageDirectory()

    override fun ls(dir: File?): List<FileMetadata> {
        val safeDir = dir ?: rootDir
        return safeDir.listFiles()
                      .map { FileMetadata(it) }
                      .sortedBy { it.name.first().toInt() }
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
}
