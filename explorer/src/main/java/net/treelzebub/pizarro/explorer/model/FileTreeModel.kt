package net.treelzebub.pizarro.explorer.model

import android.content.Context
import android.net.Uri
import net.treelzebub.pizarro.explorer.entities.FileMetadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeModel {

    fun ls(dir: File?): List<FileMetadata>
    fun exec(c: Context, uri: Uri)
}
