package com.neonusa.belajarkanjijlpt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.model.KanjiItem
import com.neonusa.belajarkanjijlpt.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubItemAdapter

    private var currentPage = 0
    private val itemsPerPage = 9

    private lateinit var kanjiItems: List<KanjiItem>
    private lateinit var kanjiSubitems: List<KanjiSubitem>

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
                showSubItems(it)
            }
            binding.recyclerview.adapter = kanjiAdapter
        }
        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }



    private fun showSubItems(item: KanjiItem) {
        val jsonString = loadJSONFromAssets("kanji_subitems.json", this)
        if (jsonString != null) {
            // Parse JSON to list
            val kanjiListType = object : TypeToken<List<KanjiSubitem>>() {}.type
            kanjiSubitems = Gson().fromJson(jsonString, kanjiListType)
            subItemAdapter = SubItemAdapter(kanjiSubitems.filter { it.kanji_item_id == item.id })
            binding.recyclerviewSubitem.adapter = subItemAdapter
        }
    }
}