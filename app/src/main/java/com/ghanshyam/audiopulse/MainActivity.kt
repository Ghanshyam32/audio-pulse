package com.ghanshyam.audiopulse


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ghanshyam.audiopulse.Models.CategoryModel
import com.ghanshyam.audiopulse.Models.SongModel
import com.ghanshyam.audiopulse.adapter.CategoryAdapter
import com.ghanshyam.audiopulse.adapter.SectionSongListAdapter
import com.ghanshyam.audiopulse.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObjects


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()
        setUpSection(
            "section_1",
            binding.section1Layout,
            binding.section1TextView,
            binding.section1recView
        )
        setUpSection(
            "section_2",
            binding.section2Layout,
            binding.section2TextView,
            binding.section2RecView
        )
        setUpMostlyPlayed(
            "mostly_played",
            binding.mostlyPlayed,
            binding.mostlyPlayedTitle,
            binding.mostlyPlayedRv
        )


    }

    override fun onResume() {
        super.onResume()
        showPlayerView()
    }

    fun showPlayerView() {
        binding.playerView.setOnClickListener {
            startActivity(Intent(this, PlayerActivity::class.java))
        }
        MyExoplayer.getCurrentSong()?.let {
            binding.playerView.visibility = View.VISIBLE
            binding.songTitle.text = it.title
            binding.songSubtitle.text = it.subtitle
            Glide.with(binding.songCoverImg).load(it.coverUrl)
                .apply(RequestOptions().transform(RoundedCorners(20)))
                .into(binding.songCoverImg)
        } ?: run {
            binding.playerView.visibility = View.GONE
        }
    }


    //Categories
    fun getCategories() {
        FirebaseFirestore.getInstance().collection("category").get().addOnSuccessListener {
            val categoryList = it.toObjects(CategoryModel::class.java)
            setUpCategoryRecyclerView(categoryList)
        }
    }

    fun setUpCategoryRecyclerView(categoryList: List<CategoryModel>) {
        categoryAdapter = CategoryAdapter(categoryList)
        binding.categoryRecView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecView.adapter = categoryAdapter
    }


    //Sections
    fun setUpSection(
        id: String,
        layout: RelativeLayout,
        title: TextView,
        recView: RecyclerView
    ) {
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {
                val section = it.toObject(CategoryModel::class.java)
                section?.apply {
//                    binding.section1Layout.visibility = View.VISIBLE
                    layout.visibility = View.VISIBLE
                    title.text = name
                    recView.layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                    recView.adapter = SectionSongListAdapter(songs)
                    layout.setOnClickListener {
                        SongsListActivity.category = section
                        startActivity(Intent(this@MainActivity, SongsListActivity::class.java))
                    }
                }
            }
    }

    fun setUpMostlyPlayed(
        id: String,
        layout: RelativeLayout,
        title: TextView,
        recView: RecyclerView
    ) {
        FirebaseFirestore.getInstance().collection("sections")
            .document(id)
            .get().addOnSuccessListener {

                FirebaseFirestore.getInstance().collection("songs")
                    .orderBy("count", Query.Direction.DESCENDING)
                    .limit(10)
                    .get().addOnSuccessListener { songListSnapshot ->
                        val songModelList = songListSnapshot.toObjects<SongModel>()
                        val songsIdList = songModelList.map {
                            it.id
                        }.toList()
                        val section = it.toObject(CategoryModel::class.java)
                        section?.apply {
//                    binding.section1Layout.visibility = View.VISIBLE
                            layout.visibility = View.VISIBLE
                            title.text = name
                            recView.layoutManager = LinearLayoutManager(
                                this@MainActivity,
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                            recView.adapter = SectionSongListAdapter(songsIdList)
                            layout.setOnClickListener {
                                SongsListActivity.category = section
                                startActivity(
                                    Intent(
                                        this@MainActivity,
                                        SongsListActivity::class.java
                                    )
                                )
                            }
                        }
                    }


            }
    }
}