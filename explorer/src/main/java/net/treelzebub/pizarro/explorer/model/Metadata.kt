package net.treelzebub.pizarro.explorer.model

import android.support.annotation.DrawableRes
import java.io.File
import java.net.URI

/**
 * Created by Tre Murillo on 3/19/16
 */
class Metadata {

    val name: String
    val size: String
    val uri: URI

    @DrawableRes val icon: Int

    constructor(file: File) {
        this.name = file.name
        this.size = if (file.isDirectory) size(dirLength(file)) else size(file.length())
        this.uri  = file.toURI()
        this.icon = icon(file)
    }

    private fun icon(file: File): Int {
        //TODO
        return when (file.extension) {
            else -> android.R.drawable.ic_menu_info_details
        }
    }

    private fun size(size: Long): String {
        return if (size < 1024) {
            String.format("%d B", size)
        } else if (size < 1024 * 1024) {
            String.format("%.1f KB", size / 1024f)
        } else if (size < 1024 * 1024 * 1024) {
            String.format("%.1f MB", size / 1024f / 1024f)
        } else {
            String.format("%.1f GB", size / 1024f / 1024f / 1024f)
        }
    }

    private fun dirLength(dir: File): Long {
        var length = 0L
        for (file in dir.listFiles()) {
            if (file.isFile) {
                length += file.length()
            } else {
                length += dirLength(file)
            }
        }
        return length
    }
}
