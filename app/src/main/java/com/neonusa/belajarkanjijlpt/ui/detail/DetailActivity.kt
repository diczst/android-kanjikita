package com.neonusa.belajarkanjijlpt.ui.detail

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.SubitemAdapter
import com.neonusa.belajarkanjijlpt.data.model.HiraganaKatakanaItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.databinding.ActivityDetailBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.utils.generateDummyKOTD
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets
import java.util.Locale

class DetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubitemAdapter

    private var currentPage = 0
    private val itemsPerPage = 9

    private lateinit var kanjiItems: List<KanjiItem>
    private lateinit var kanjiSubitems: List<KanjiSubitem>

    private lateinit var tts: TextToSpeech
    private lateinit var jlptLevel: String

    private var jsonString: String? = null
    private var jsonStringKanjiSubitem: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        loadAds()

        jsonString = loadJSONFromAssets("kanji_items.json", this)
        // Parse JSON to list
        val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
        kanjiItems = Gson().fromJson(jsonString, kanjiListType)

        jsonStringKanjiSubitem = loadJSONFromAssets("kanji_subitems.json", this)
        // Parse JSON to list
        val kanjiSubitemListType = object : TypeToken<List<KanjiSubitem>>() {}.type
        kanjiSubitems = Gson().fromJson(jsonStringKanjiSubitem, kanjiSubitemListType)

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

                val start = currentPage * itemsPerPage
                val end = minOf(start + itemsPerPage, kanjiItems.size)
                val pageData = kanjiItems.subList(start, end)
                loadKanjiData()
                showSubItems(pageData.first())
            }
        }

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        loadKanjiData()
        showSubItems(kanjiItems.first())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
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
                speakKanji(it)
            }
            binding.recyclerview.adapter = kanjiAdapter
        }
        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }

    private fun showSubItems(item: KanjiItem){
        val kotdAdapter = SubitemAdapter(kanjiSubitems.filter { it.kanji_item_id == item.id })

        binding.recyclerviewSubitem.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewSubitem.adapter = kotdAdapter

    }

    private fun loadAds(){
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = getResources().getString(R.string.sample_adunit_banner)
        binding.adviewContainerMain.addView(adView)
        // Request
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    // Fungsi untuk memutar suara TTS
    private fun speakKanji(kanjiItem: KanjiItem) {
        val text = kanjiItem.kanji
        if (text!!.isNotEmpty()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE) // Atur bahasa ke Jepang
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "Bahasa tidak didukung", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Inisialisasi TextToSpeech gagal", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}