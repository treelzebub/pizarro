package net.treelzebub.pizarro

import android.app.Application

/**
 * Created by Tre Murillo on 3/19/16
 */
class PizarroApplication : Application() {

    companion object {
        const val PREFS_FILE = "pizarro_prefs"
    }

    override fun onCreate() {
        super.onCreate()
        BaseInjection.context = this
        BaseInjection.prefs = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
    }
}
