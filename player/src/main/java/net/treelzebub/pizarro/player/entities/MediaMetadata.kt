package net.treelzebub.pizarro.player.entities

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever

/**
 * Created by Tre Murillo on 3/20/16
 */
class MediaMetadata {

    var image: Bitmap?  = null
    var artist: String? = null
    var album: String?  = null
    var track: String?  = null
    var length: String? = null

    constructor(path: String) {
        val mmr = MediaMetadataRetriever().apply { setDataSource(path) }
        val img = mmr.embeddedPicture
        if (img != null) {
            this.image = BitmapFactory.decodeByteArray(img, 0, img.size)
        }
        this.artist = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
                ?: mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUMARTIST)
                ?: mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_COMPOSER)
                ?: mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_AUTHOR)
        this.album = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
        this.track = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        this.length = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        mmr.release()
    }
}
