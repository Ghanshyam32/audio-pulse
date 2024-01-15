package com.ghanshyam.audiopulse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ghanshyam.audiopulse.Models.CategoryModel
import com.ghanshyam.audiopulse.adapter.SongsListAdapter
import com.ghanshyam.audiopulse.databinding.ActivitySongsListBinding

class SongsListActivity : AppCompatActivity() {

    companion object {
        lateinit var category: CategoryModel
    }

    lateinit var binding: ActivitySongsListBinding
    lateinit var songsListAdapter: SongsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.categoryName2.text = category.name
        Glide.with(binding.coverImage).load(category.coverUrl)
            .apply(RequestOptions().transform(RoundedCorners(32))).into(binding.coverImage)

        setUpSongsListRecyclerView()
    }

    fun setUpSongsListRecyclerView() {
        songsListAdapter = SongsListAdapter(category.songs)
        binding.songsListRecView.layoutManager = LinearLayoutManager(this)
        binding.songsListRecView.adapter = songsListAdapter
    }

}