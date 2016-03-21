package net.treelzebub.pizarro.player.activity

import android.app.Notification
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import net.treelzebub.pizarro.player.R

/**
 * Created by Tre Murillo on 3/20/16
 */
class PlayerActivity : AppCompatActivity() {

    companion object {
        const val UID = 90872354
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val style = NotificationCompat.MediaStyle()
        val notif = NotificationCompat.Builder(this)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_STATUS)
                .setStyle(style)
                .setSmallIcon(R.drawable.ic_player)
                .setContentTitle("Pizarro Player")
                .setContentText("Someday, this will play music and podcasts!")
                .build()
        val notifMgr = NotificationManagerCompat.from(this)
        notifMgr.notify(UID, notif)
    }
}
