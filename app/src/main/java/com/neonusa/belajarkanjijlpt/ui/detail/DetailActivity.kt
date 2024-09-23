package com.neonusa.belajarkanjijlpt.ui.detail

import DetailViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordAdapter
import com.neonusa.belajarkanjijlpt.adapter.SubitemAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.databinding.ActivityDetailBinding
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class DetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var tts: TextToSpeech
    private lateinit var jlptLevel: String
    private val detailViewModel: DetailViewModel by viewModel()

    // KANJI
    //==============================================================
    private var jsonKanjiSingleString: String? = null
    private lateinit var kanjiSingleItems: List<KanjiItem>
    private lateinit var kanjiSingleItemsFilteredByLevel: List<KanjiItem>
    private var currentPage = 0
    private val itemsPerPage = 9
    //==============================================================

    // KANJI WORDS
    //==============================================================
    private var jsonKanjiWordString: String? = null
    private lateinit var kanjiWordItems: List<KanjiWord>
    //==============================================================

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadAds()

        tts = TextToSpeech(this, this)
        jlptLevel = intent.getStringExtra(JLPT_LEVEL)!!

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitleDetail.text = "JLPT $jlptLevel"

        binding.prevButton.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                loadKanjiData()
            }
        }

        binding.nextButton.setOnClickListener {
            if ((currentPage + 1) * itemsPerPage < kanjiSingleItemsFilteredByLevel.size) {
                currentPage++

                val start = currentPage * itemsPerPage
                val end = minOf(start + itemsPerPage, kanjiSingleItemsFilteredByLevel.size)
                val pageData = kanjiSingleItemsFilteredByLevel.subList(start, end)
                loadKanjiData()
//                showSubItems(pageData.first())
            }
        }


        jsonKanjiSingleString = loadJSONFromAssets("kanji_single.json", this) // Parse JSON string
        val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
        kanjiSingleItems = Gson().fromJson(jsonKanjiSingleString, kanjiListType) // Parse string json to list
        kanjiSingleItemsFilteredByLevel = kanjiSingleItems.filter { it.level!! == jlptLevel } // filter list
        binding.rvSingleKanji.layoutManager = GridLayoutManager(this, 3)


        jsonKanjiWordString = loadJSONFromAssets("kanji_words.json", this)
        detailViewModel.insertJsonDataToDatabase(jsonKanjiWordString.toString()) // Insert data dari json ke sqlite
        detailViewModel.kanjis.observe(this) { kanjiList -> // Memuat data dari sqlite
            val firstKanji = kanjiSingleItemsFilteredByLevel.first()
            val filteredKanjiWordItems = kanjiList.filter { it.kanji_word.contains(
                firstKanji.kanji.toString())}
            val kanjiWordAdapter = KanjiWordAdapter(filteredKanjiWordItems){
                // when item clicked
                speakKanji(kanjiWord = it)
            }
            binding.rvKanjiWords.layoutManager = LinearLayoutManager(this)
            binding.rvKanjiWords.adapter = kanjiWordAdapter
        // Log.d(this::class.simpleName, "onCreate: $kanjiList")
        }

        loadKanjiData()
//        showSubItems(kanjiItems.first())
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun loadKanjiWordData(kanjiSingle: KanjiItem){
        detailViewModel.kanjis.observe(this) { kanjiList -> // Memuat data dari sqlite
            val filteredKanjiWordItems = kanjiList.filter { it.kanji_word.contains(
                kanjiSingle.kanji.toString())}
            val kanjiWordAdapter = KanjiWordAdapter(filteredKanjiWordItems){
                // when item clicked
                speakKanji(kanjiWord = it)
            }
            binding.rvKanjiWords.layoutManager = LinearLayoutManager(this)
            binding.rvKanjiWords.adapter = kanjiWordAdapter
            // Log.d(this::class.simpleName, "onCreate: $kanjiList")
        }
    }

    private fun loadKanjiData(){
        val start = currentPage * itemsPerPage

        if (jsonKanjiSingleString != null) {
//            val filteredByJLPTLevel = kanjiItems.filter { it.JLPTLevel == jlptLevel }
                val end = minOf(start + itemsPerPage, kanjiSingleItemsFilteredByLevel.size)
            val pageData = kanjiSingleItemsFilteredByLevel.subList(start, end)
            val kanjiAdapter = GridAdapter(pageData){
                // when item clicked
//                showSubItems(it)
                speakKanji(kanjiItem = it)
                loadKanjiWordData(it)
            }
            binding.rvSingleKanji.adapter = kanjiAdapter
        }
        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }

//    private fun showSubItems(item: KanjiItem){
//        val kotdAdapter = SubitemAdapter(kanjiSubitems.filter { it.kanji_item_id == item.id })
//        binding.recyclerviewSubitem.layoutManager = LinearLayoutManager(this)
//        binding.recyclerviewSubitem.adapter = kotdAdapter
//
//    }

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
    private fun speakKanji(kanjiItem: KanjiItem?= null, kanjiWord: KanjiWord?=null) {
        if(kanjiItem != null){
            val text = kanjiItem.kanji
            if (text!!.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
            val text = kanjiWord?.furigana
            if (text!!.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
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

    companion object {
        const val JLPT_LEVEL = "JLPT_LEVEL"
    }
}