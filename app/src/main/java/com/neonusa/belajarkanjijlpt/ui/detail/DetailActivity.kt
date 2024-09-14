package com.neonusa.belajarkanjijlpt.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.SubitemAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.databinding.ActivityDetailBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubitemAdapter

    private var currentPage = 0
    private val itemsPerPage = 9

    private lateinit var kanjiItems: List<KanjiItem>
    private lateinit var kanjiSubitems: List<KanjiSubitem>
    private lateinit var jlptLevel: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        loadKanjiData()
    }

    private fun loadKanjiData(){
        val start = currentPage * itemsPerPage

        val jsonString = loadJSONFromAssets("kanji_items.json", this)
        if (jsonString != null) {
            // Parse JSON to list
            val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
            kanjiItems = Gson().fromJson(jsonString, kanjiListType)
            val filteredByJLPTLevel = kanjiItems.filter { it.JLPTLevel == jlptLevel }

                val end = minOf(start + itemsPerPage, kanjiItems.size)
            val pageData = kanjiItems.subList(start, end)
            val kanjiAdapter = GridAdapter(pageData){
                // when item clicked
//                showSubItems(it)
            }
            binding.recyclerview.adapter = kanjiAdapter
        }
        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }
}