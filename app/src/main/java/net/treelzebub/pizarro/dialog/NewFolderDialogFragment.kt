package net.treelzebub.pizarro.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import net.treelzebub.pizarro.R
import net.treelzebub.pizarro.activity.OnNewFolderListener

/**
 * Created by Tre Murillo on 3/20/16
 */
class NewFolderDialogFragment : DialogFragment() {

    var listener: OnNewFolderListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_folder, container)
        val name = view.findViewById(R.id.new_folder_name) as EditText
        val go = view.findViewById(R.id.go)
        go.setOnClickListener {
            val folder = name.text.toString()
            if (folder.isNullOrBlank()) {
                Toast.makeText(context, "Folder name cannot be blank.", Toast.LENGTH_SHORT).show()
            } else {
                listener?.onNewFolder(folder)
                dismiss()
            }
        }
        return view
    }
}
