package net.treelzebub.pizarro.listener

import android.support.v7.widget.RecyclerView
import android.view.MotionEvent

/**
 * Created by Tre Murillo on 3/19/16
 */
interface  FileTreeOnTouchListener : RecyclerView.OnItemTouchListener {
    override fun onInterceptTouchEvent(rv: RecyclerView, event: MotionEvent): Boolean {
        return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

    override fun onTouchEvent(rv: RecyclerView, event: MotionEvent) {
    }
}
