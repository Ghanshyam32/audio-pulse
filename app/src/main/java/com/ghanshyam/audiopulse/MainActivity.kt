package com.ghanshyam.audiopulse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ghanshyam.audiopulse.Models.CategoryModel
import com.ghanshyam.audiopulse.adapter.CategoryAdapter
import com.ghanshyam.audiopulse.adapter.SectionSongListAdapter
import com.ghanshyam.audiopulse.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var categoryAdapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getCategories()
        setUpSection()

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
    fun setUpSection() {
        FirebaseFirestore.getInstance().collection("sections")
            .document("section_1")
            .get().addOnSuccessListener {
                val section = it.toObject(CategoryModel::class.java)
                section?.apply {
                    binding.trendingTextView.text = name
                    binding.trendingRecView.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.trendingRecView.adapter = SectionSongListAdapter(songs)
                }
            }
    }
}