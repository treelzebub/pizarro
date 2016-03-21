package net.treelzebub.pizarro.player.activity

import android.app.Notification
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaMetadataRetriever.*
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.NotificationCompat
import android.view.DragEvent
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import butterknife.bindView
import net.treelzebub.pizarro.player.R
import net.treelzebub.pizarro.player.entities.MediaMetadata

/**
 * Created by Tre Murillo on 3/20/16
 */
class PlayerActivity : AppCompatActivity() {

    companion object {
        const val UID = 90872354
        const val SEEK_DEFAULT = 15000
    }

    private val trackBack: View         by bindView(R.id.track_back)
    private val rewind: View            by bindView(R.id.rewind)
    private val playPause: ImageView    by bindView(R.id.play_pause)
    private val stop: View              by bindView(R.id.stop)
    private val fastForward: View       by bindView(R.id.fast_forward)
    private val trackForward: View      by bindView(R.id.track_forward)
    private val progress: ProgressBar   by bindView(R.id.progress)

    private val mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player)
        setUpControls()
        setUpProgressBar()
        if (intent.data != null) {
            val path = intent.data.path
//            createNotif(metadata(path))
            play(path)
        }
        mediaPlayer.setOnCompletionListener {
            playPause.setImageResource(R.drawable.ic_play)
            progress.progress = 0
        }
        mediaPlayer.setOnPreparedListener {
            async() {
                progress.max = it.duration
                while (mediaPlayer.isPlaying) {
                    progress.progress = mediaPlayer.currentPosition
                }
            }
        }
        mediaPlayer.setOnSeekCompleteListener {
            progress.progress = it.currentPosition
        }
    }

    private fun setUpControls() {
        trackBack.setOnClickListener {
            //TODO playlist!
            maybeDo { mediaPlayer.seekTo(0) }
        }
        rewind.setOnClickListener {
            maybeDo {
                mediaPlayer.seekTo(mediaPlayer.currentPosition - SEEK_DEFAULT) // rewind 30 seconds
            }
        }
        playPause.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
                playPause.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer.start()
                playPause.setImageResource(R.drawable.ic_pause)
            }
        }
        fastForward.setOnClickListener {
            maybeDo {
                mediaPlayer.seekTo(mediaPlayer.currentPosition + SEEK_DEFAULT)
            }
        }
        stop.setOnClickListener {
            maybeDo {
                mediaPlayer.stop()
            }
        }
        trackForward.setOnClickListener {
            // TODO playlist!
        }
    }

    private fun setUpProgressBar() {
//        progress
    }

    private fun createNotif(meta: MediaMetadata) {
        val notif = NotificationCompat.Builder(this)
                .setOngoing(true)
                .setCategory(Notification.CATEGORY_STATUS)
                .setStyle(NotificationCompat.MediaStyle())
                .setSmallIcon(R.drawable.ic_player)
                .setLargeIcon(meta.image ?: BitmapFactory.decodeResource(resources, R.drawable.ic_player))
                .setContentTitle(meta.track ?: "Pizarro Player")
                .setContentText(meta.artist ?: "")
                .build()
        val notifMgr = NotificationManagerCompat.from(this)
        notifMgr.notify(UID, notif)
    }

    //TODO probably mv this to MediaMetadata constructor(path: String)
    private fun metadata(path: String): MediaMetadata {
        val mmr = MediaMetadataRetriever().apply { setDataSource(path) }
        val retval = MediaMetadata().apply {
            val img = mmr.embeddedPicture
            if (img != null) {
                image = BitmapFactory.decodeByteArray(img, 0, img.size)
            }
            artist = mmr.extractMetadata(METADATA_KEY_ARTIST)
                    ?: mmr.extractMetadata(METADATA_KEY_ALBUMARTIST)
                    ?: mmr.extractMetadata(METADATA_KEY_COMPOSER)
                    ?: mmr.extractMetadata(METADATA_KEY_AUTHOR)
            album = mmr.extractMetadata(METADATA_KEY_ALBUM)
            track = mmr.extractMetadata(METADATA_KEY_TITLE)
            length = mmr.extractMetadata(METADATA_KEY_DURATION)
        }
        mmr.release()
        return retval
    }

    private fun play(path: String) {
        try {
            mediaPlayer.apply {
                setDataSource(path)
                prepare()
                start()
                playPause.setImageResource(R.drawable.ic_pause)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun maybeDo(fn: () -> Unit) {
        if (mediaPlayer.isPlaying) {
            fn()
        }
    }
}

fun async(fn: () -> Unit) {
    object : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            fn()
            return null
        }
    }.execute(null)
}