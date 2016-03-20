package net.treelzebub.pizarro.explorer.entities

import android.support.annotation.DrawableRes
import net.treelzebub.pizarro.explorer.R
import java.io.File
import java.net.URI

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileMetadata {

    val name: String
    val size: String
    val uri: URI
    @DrawableRes val icon: Int

    constructor(file: File) {
        this.name = file.name
        this.size = if (file.isDirectory) size(dirLength(file)) else size(file.length())
        this.uri  = file.toURI()
        this.icon = drawableRes(file)
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

    val imageFormats = listOf("gif", "png", "jpg", "jpeg", "svg", "tif", "tiff")
    val audioFormats = listOf("mp3", "wav", "ogg", "flac", "3ga", "wma", "midi")
    val videoFormats = listOf("mp4", "avi", "mpeg", "mpg", "mov", "qt", "flv", "swf",
                              "asf", "wmv", "h.264", "divx")
    private fun drawableRes(file: File): Int {
        return if (file.isDirectory) {
            R.drawable.ic_folder
        } else {
            when (file.extension.toLowerCase()) {
                in imageFormats -> R.drawable.ic_image_file
                in audioFormats -> R.drawable.ic_audio_file
                in videoFormats -> R.drawable.ic_video_file
                else            -> R.drawable.ic_unknown_file
            }
        }
    }
}