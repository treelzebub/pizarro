package net.treelzebub.pizarro.adapter

import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import net.treelzebub.kapsule.extensions.TAG
import net.treelzebub.kapsule.extensions.inflate
import net.treelzebub.kapsule.utils.KPrefs
import net.treelzebub.pizarro.BaseInjection
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.explorer.model.FileTreeModel
import net.treelzebub.pizarro.explorer.model.Metadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeAdapter : RecyclerView.Adapter<FileTreeItemHolder>(), FileTreeListener {

    companion object {
        const val LAST_DIR = "last_known_dir"
    }

    private val prefs: SharedPreferences get() = BaseInjection.prefs
    private val lastKnownDir: String?    get() = KPrefs.get(prefs, LAST_DIR)

    private val layout = R.layout.item_file

    var items: List<Metadata> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileTreeItemHolder {
        return FileTreeItemHolder(parent.inflate(layout), this)
    }

    override fun onBindViewHolder(holder: FileTreeItemHolder, position: Int) {
        holder.metadata = items.elementAt(position)
        Log.d(TAG, position.toString())
    }

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long {
        return items.elementAtOrNull(position)?.hashCode()?.toLong() ?: -1L
    }

    override fun getItemViewType(position: Int) = 0

    fun refresh() {
        items = FileTreeModel.ls()
    }

    override fun onChangeDir(dir: File) {
        items = FileTreeModel.ls(dir)
        KPrefs.put(prefs, LAST_DIR, dir.toURI().toString())
    }
}

