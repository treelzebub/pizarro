package net.treelzebub.pizarro.adapter

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.bindView
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.explorer.model.FileMetadata
import java.io.File

/**
 * Created by Tre Murillo on 3/19/16
 */
class FileTreeItemHolder(val v: View, val listener: FileTreeListener) : RecyclerView.ViewHolder(v) {
    val icon: ImageView         by bindView(R.id.icon)
    val filenameText: TextView  by bindView(R.id.filename)
    val metadataText: TextView  by bindView(R.id.metadata)

    var metadata: FileMetadata? = null
        set(value) {
            field = value ?: return
            icon.setImageDrawable(ContextCompat.getDrawable(v.context, value.icon))
            filenameText.text = value.name
            metadataText.text = value.size
            itemView.setOnClickListener {
                listener.onChangeDir(File(value.uri))
            }
        }
}
