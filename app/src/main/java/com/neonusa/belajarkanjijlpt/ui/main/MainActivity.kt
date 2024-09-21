package com.neonusa.belajarkanjijlpt.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.HiraganaKatakanaAdapter
import com.neonusa.belajarkanjijlpt.adapter.JLPTLevelAdapter
import com.neonusa.belajarkanjijlpt.adapter.SubitemAdapter
import com.neonusa.belajarkanjijlpt.data.model.JLPTLevelItem
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.ui.detail.DetailActivity
import com.neonusa.belajarkanjijlpt.ui.learned.LearnedActivity
import com.neonusa.belajarkanjijlpt.ui.letter.LetterActivity
import com.neonusa.belajarkanjijlpt.utils.generateDummyKOTD
import com.neonusa.belajarkanjijlpt.utils.hiraganaGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaGenerator
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubitemAdapter

    private lateinit var kanjiSubitems: List<KanjiSubitem>

    val jlptLevels = listOf(
        JLPTLevelItem("N5", R.drawable.one), // Gambar PNG untuk N5
        JLPTLevelItem("N4", R.drawable.two), // Gambar PNG untuk N4
        JLPTLevelItem("N3", R.drawable.three), // Gambar PNG untuk N3
        JLPTLevelItem("N2", R.drawable.four), // Gambar PNG untuk N2
        JLPTLevelItem("N1", R.drawable.five)  // Gambar PNG untuk N1
    )

    private val hiraganaItems = hiraganaGenerator().take(10)
    private val katakanaItems = katakanaGenerator().take(10)
    private val kanjiOfTheDayItems = generateDummyKOTD().shuffled().take(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAds()

//        setSupportActionBar(binding.toolbar)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)
//        supportActionBar!!.title = "Belajar Kanji"

        val jlptLevelAdapter = JLPTLevelAdapter(jlptLevels){
            startActivity(Intent(this,DetailActivity::class.java))
        }
        binding.rvLevels.layoutManager = GridLayoutManager(this, 5)
        binding.rvLevels.adapter = jlptLevelAdapter

        val hiraganaAdapter = HiraganaKatakanaAdapter(hiraganaItems){}
//        binding.rvHiragana.layoutManager = GridLayoutManager(this, 5)
//        binding.rvHiragana.adapter = hiraganaAdapter
//
//        val katakanaAdapter = HiraganaKatakanaAdapter(katakanaItems){}
//        binding.rvKatakana.layoutManager = GridLayoutManager(this, 5)
//        binding.rvKatakana.adapter = katakanaAdapter

        val kotdAdapter = SubitemAdapter(kanjiOfTheDayItems)
        binding.rvKotd.layoutManager = LinearLayoutManager(this)
        binding.rvKotd.adapter = kotdAdapter

//        binding.tvLearned.setOnClickListener {
//            val intent = Intent(this,LearnedActivity::class.java)
//            startActivity(intent)
//        }

//        binding.tvHiragana.setOnClickListener {
//            val intent = Intent(this,LetterActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.tvKatakana.setOnClickListener {
//            val intent = Intent(this,LetterActivity::class.java)
//            startActivity(intent)
//        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
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
}