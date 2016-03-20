package net.treelzebub.pizarro

import android.app.Application

/**
 * Created by Tre Murillo on 3/19/16
 */
class PizarroApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        BaseInjection.context = this
    }
}
