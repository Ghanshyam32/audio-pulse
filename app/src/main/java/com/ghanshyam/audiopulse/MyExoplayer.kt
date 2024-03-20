package com.ghanshyam.audiopulse

import android.content.ContentValues.TAG
import androidx.media3.exoplayer.ExoPlayer
import com.ghanshyam.audiopulse.Models.SongModel
import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore

object MyExoplayer {

    private var exoPlayer: ExoPlayer? = null
    private var currentSong: SongModel? = null

    fun getCurrentSong(): SongModel? {
        return currentSong
    }

    fun getInstance(): ExoPlayer? {

        return exoPlayer
    }

    fun startPlaying(context: Context, song: SongModel) {
        if (exoPlayer == null)
            exoPlayer = ExoPlayer.Builder(context).build()

        if (currentSong != song) {
            //Its a new song so start playing
            currentSong = song
            updateCount()
            currentSong = song
            currentSong?.url?.apply {
                val mediaItem = MediaItem.fromUri(this)
                exoPlayer?.setMediaItem(mediaItem)
                exoPlayer?.prepare()
                exoPlayer?.play()
            }
        }
    }

    fun updateCount() {
        currentSong?.id?.let { id ->
            val db = FirebaseFirestore.getInstance()
            val docRef = db.collection("songs").document(id)

            docRef.get()
                .addOnSuccessListener { document ->
                    val latestCount = document.getLong("count") ?: 0
                    val newCount = latestCount + 1

                    docRef.update("count", newCount)
                        .addOnSuccessListener {
                            Log.d(TAG, "Count updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e(TAG, "Error updating count", e)
                        }
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error getting document", e)
                }
        }
    }


}