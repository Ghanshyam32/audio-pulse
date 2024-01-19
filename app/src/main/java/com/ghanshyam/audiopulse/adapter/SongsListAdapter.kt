package com.ghanshyam.audiopulse.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ghanshyam.audiopulse.Models.SongModel
import com.ghanshyam.audiopulse.MyExoplayer
import com.ghanshyam.audiopulse.SongsListActivity
import com.ghanshyam.audiopulse.databinding.ActivitySongsListBinding
import com.ghanshyam.audiopulse.databinding.SongItemBinding
import com.google.firebase.firestore.FirebaseFirestore

class SongsListAdapter(private val songIdList: List<String>) :
    RecyclerView.Adapter<SongsListAdapter.MyViewHolder>() {
    class MyViewHolder(private val binding: SongItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(songId: String) {
            FirebaseFirestore.getInstance().collection("songs")
                .document(songId).get()
                .addOnSuccessListener {
                    val song = it.toObject(SongModel::class.java)
                    song?.apply {

                        binding.songTitle.text = song.title
                        binding.songSubtitle.text = song.subtitle
                        Glide.with(binding.songCover).load(coverUrl)
                            .apply(RequestOptions().transform(RoundedCorners(10)))
                            .into(binding.songCover)
                        binding.root.setOnClickListener {
                            MyExoplayer.startPlaying(binding.root.context, song)
                        }

                    }
                }
//            binding.songTitle.text = songId
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            SongItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return songIdList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(songIdList[position])
    }
}