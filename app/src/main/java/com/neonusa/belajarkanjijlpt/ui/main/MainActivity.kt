package com.neonusa.belajarkanjijlpt.ui.main

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.widget.Toast
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
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordAdapter
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordOfTheDayAdapter
import com.neonusa.belajarkanjijlpt.data.model.JLPTLevelItem
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiSubitem
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.ui.detail.DetailActivity
import com.neonusa.belajarkanjijlpt.ui.learned.LearnedActivity
import com.neonusa.belajarkanjijlpt.ui.letter.LetterActivity
import com.neonusa.belajarkanjijlpt.utils.MyPreference
import com.neonusa.belajarkanjijlpt.utils.generateDummyKOTD
import com.neonusa.belajarkanjijlpt.utils.hiraganaGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaGenerator
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()
    private lateinit var tts: TextToSpeech

    val jlptLevels = listOf(
        JLPTLevelItem("N5", R.drawable.one), // Gambar PNG untuk N5
        JLPTLevelItem("N4", R.drawable.two), // Gambar PNG untuk N4
        JLPTLevelItem("N3", R.drawable.three), // Gambar PNG untuk N3
        JLPTLevelItem("N2", R.drawable.four), // Gambar PNG untuk N2
        JLPTLevelItem("N1", R.drawable.five)  // Gambar PNG untuk N1
    )

    private lateinit var kanjiWordOfTheDayAdapter: KanjiWordOfTheDayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAds()
        languageSetup()

        val jlptLevelAdapter = JLPTLevelAdapter(jlptLevels){
            val intent = Intent(this,DetailActivity::class.java)
            intent.putExtra(DetailActivity.JLPT_LEVEL,it.level)
            startActivity(intent)
        }
        binding.rvLevels.layoutManager = GridLayoutManager(this, 5)
        binding.rvLevels.adapter = jlptLevelAdapter

        binding.layoutHiragana.setOnClickListener {
            val intent = Intent(this, LetterActivity::class.java)
            intent.putExtra(LetterActivity.LETTER_TYPE, LetterActivity.HIRAGANA)
            startActivity(intent)
        }

        binding.layoutKatakana.setOnClickListener {
            val intent = Intent(this, LetterActivity::class.java)
            intent.putExtra(LetterActivity.LETTER_TYPE, LetterActivity.KATAKANA)
            startActivity(intent)
        }

        binding.rvKotd.layoutManager = LinearLayoutManager(this)
        kanjiWordOfTheDayAdapter = KanjiWordOfTheDayAdapter(
            onItemClick = {speakKanji(kanjiWord = it)},
            onBookmarkClick =  {mainViewModel.updateBookmarkStatus(it.id,!it.is_checked)
            })
        binding.rvKotd.adapter = kanjiWordOfTheDayAdapter
        mainViewModel.kanjisOfTheDay.observe(this){
            kanjiWordOfTheDayAdapter.submitList(it.take(4))
        }


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

    private fun languageSetup(){
        val defaultLocale = Locale.getDefault()
        if(MyPreference.lang == "default"){
            if(defaultLocale.language.equals("in")){
                MyPreference.lang = "in"
            } else {
                MyPreference.lang = "en"
            }
        }
    }

    // Fungsi untuk memutar suara TTS
    private fun speakKanji(kanjiItem: KanjiItem?= null, kanjiWord: KanjiWord?=null) {
        if(kanjiItem != null){
            val text = kanjiItem.kanji
            if (text!!.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
            val text = kanjiWord?.kanji_word
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