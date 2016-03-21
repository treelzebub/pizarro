package net.treelzebub.pizarro.player

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by Tre Murillo on 3/20/16
 */
class MediaService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
    }
}
