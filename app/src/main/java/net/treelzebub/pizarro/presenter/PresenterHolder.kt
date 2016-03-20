package net.treelzebub.pizarro.presenter

import java.util.*

/**
 * Created by Tre Murillo on 3/19/16
 */
object PresenterHolder {

    private val presenterMap: HashMap<Class<*>, Presenter> = hashMapOf()

    fun putPresenter(c: Class<*>, p: Presenter) {
        presenterMap.put(c, p)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Presenter> getPresenter(c: Class<T>): T? {
        return presenterMap[c] as T?
    }

    fun remove(c: Class<*>) {
        presenterMap.remove(c)
    }
}