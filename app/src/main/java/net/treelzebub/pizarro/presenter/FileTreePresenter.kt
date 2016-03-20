package net.treelzebub.pizarro.presenter

import android.content.Context
import android.net.Uri

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreePresenter : Presenter {

    var view: FileTreeView?
    fun create()
    fun changeDirOrOpen(c: Context, uri: Uri)
}
