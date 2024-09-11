package com.neonusa.belajarkanjijlpt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.model.KanjiItem
import com.neonusa.belajarkanjijlpt.utils.generateDummySubitemKanjiData
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubItemAdapter

    private var currentPage = 0
    private val itemsPerPage = 9

    private lateinit var kanjiItems: List<KanjiItem>
    val subitemData = generateDummySubitemKanjiData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        loadKanjiData()

        binding.prevButton.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                loadKanjiData()
            }
        }

        binding.nextButton.setOnClickListener {
            if ((currentPage + 1) * itemsPerPage < kanjiItems.size) {
                currentPage++
                loadKanjiData()
            }
        }
    }

    private fun loadKanjiData(){
        val start = currentPage * itemsPerPage

        val jsonString = loadJSONFromAssets("kanji_items.json", this)
        if (jsonString != null) {
            // Parse JSON to list
            val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
            kanjiItems = Gson().fromJson(jsonString, kanjiListType)

            val end = minOf(start + itemsPerPage, kanjiItems.size)
            val pageData = kanjiItems.subList(start, end)
            val kanjiAdapter = GridAdapter(pageData){
                // when item clicked

            }
            binding.recyclerview.adapter = kanjiAdapter
        }
    }


    private fun updateRecyclerView() {
        val start = currentPage * itemsPerPage
//        val end = minOf(start + itemsPerPage, kanjiData.size)
//        val pageData = kanjiData.subList(start, end)
//
//        adapter = GridAdapter(pageData) {
//            showSubItems(it)
//        }
        binding.recyclerview.adapter = adapter
        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }

    private fun showSubItems(item: KanjiItem) {
        // Filter subitems based on the selected KanjiItem
        val subItems = subitemData.filter { it.kanji_item_id == item.id }
        subItemAdapter = SubItemAdapter(subItems)
        binding.recyclerviewSubitem.adapter = subItemAdapter
    }
}