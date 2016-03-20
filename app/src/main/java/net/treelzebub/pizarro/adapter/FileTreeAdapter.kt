package net.treelzebub.pizarro.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import net.treelzebub.kapsule.extensions.TAG
import net.treelzebub.kapsule.extensions.inflate
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.explorer.model.FileMetadata

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeAdapter : RecyclerView.Adapter<FileTreeItemHolder>() {

    init {
        setHasStableIds(true)
    }

    var treeItems: List<FileMetadata> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val layout = R.layout.item_file

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileTreeItemHolder {
        return FileTreeItemHolder(parent.inflate(layout))
    }

    override fun onBindViewHolder(holder: FileTreeItemHolder, position: Int) {
        holder.metadata = treeItems.elementAt(position)
        Log.d(TAG, position.toString())
    }

    override fun getItemCount() = treeItems.size

    override fun getItemId(position: Int): Long {
        return treeItems.elementAtOrNull(position)?.hashCode()?.toLong() ?: -1L
    }

    override fun getItemViewType(position: Int) = 0

    fun getItem(position: Int): FileMetadata? {
        return treeItems.elementAtOrNull(position)
    }
}

