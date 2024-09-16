package com.neonusa.belajarkanjijlpt.ui.detail

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.SubitemAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.databinding.ActivityDetailBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.utils.generateDummyKOTD
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubitemAdapter

    private var currentPage = 0
    private val itemsPerPage = 9

    private lateinit var kanjiItems: List<KanjiItem>
    private val kanjiOfTheDayItems = generateDummyKOTD()

    private lateinit var kanjiSubitems: List<KanjiSubitem>
    private lateinit var jlptLevel: String

    private var jsonString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        jsonString = loadJSONFromAssets("kanji_items.json", this)
        // Parse JSON to list
        val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
        kanjiItems = Gson().fromJson(jsonString, kanjiListType)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

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

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        loadKanjiData()
        showSubItems(kanjiItems.first())
    }

    private fun loadKanjiData(){
        val start = currentPage * itemsPerPage

        if (jsonString != null) {
//            val filteredByJLPTLevel = kanjiItems.filter { it.JLPTLevel == jlptLevel }
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

    private fun showSubItems(item: KanjiItem){
        val kotdAdapter = SubitemAdapter(kanjiOfTheDayItems.filter { it.kanji_item_id == item.id })
        binding.recyclerviewSubitem.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSubitem.adapter = kotdAdapter
    }
}