package net.treelzebub.pizarro.adapter

import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
interface FileTreeListener {

    fun onChangeDir(dir: File)
}
