package com.ghanshyam.audiopulse

import androidx.media3.exoplayer.ExoPlayer
import com.ghanshyam.audiopulse.Models.SongModel
import android.content.Context
import androidx.media3.common.MediaItem

object MyExoplayer {

    private var exoPlayer: ExoPlayer? = null
    private var currentSong: SongModel? = null

    fun getInstance(): ExoPlayer? {

        return exoPlayer
    }

    fun startPlaying(context: Context, song: SongModel) {
        if (exoPlayer == null)
            exoPlayer = ExoPlayer.Builder(context).build()

        if (currentSong != song) {
            //Its a new song so start playing
            currentSong = song
         
            currentSong = song
            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
            }
        }
    }

}