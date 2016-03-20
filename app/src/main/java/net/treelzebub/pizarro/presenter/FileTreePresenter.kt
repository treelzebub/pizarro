package net.treelzebub.pizarro.presenter

import android.content.Context
import net.treelzebub.pizarro.explorer.entities.FileMetadata

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreePresenter : Presenter {

    var view: FileTreeView?
    fun create()
    fun reload()
    fun changeDirOrOpen(c: Context, data: FileMetadata)
    fun mkDir(name: String): Boolean
    fun rm(data: FileMetadata): Boolean
    fun canGoBack(): Boolean
    fun onBack()
}
