package com.neonusa.belajarkanjijlpt.ui.learned

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.neonusa.belajarkanjijlpt.BuildConfig
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordAdapter
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordOfTheDayAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.databinding.ActivityLearnedBinding
import com.neonusa.belajarkanjijlpt.ui.main.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class LearnedActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLearnedBinding
    private val learnedViewModel: LearnedViewModel by viewModel()
    private lateinit var kanjiWordAdapter: KanjiWordAdapter
    private lateinit var tts: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLearnedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        loadAds()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        learnedViewModel.getCheckedKanjiCount().observe(this){ learnedKanjiCount ->
            learnedViewModel.getKanjiCount { totalKanjiCount ->
                binding.tvTotalKanji.text = totalKanjiCount.toString()
                binding.tvLearnedKanji.text = learnedKanjiCount.toString()
                if (totalKanjiCount > 0) {
                    val progress = (learnedKanjiCount * 100) / totalKanjiCount
                    binding.pbarKanjiLearned.progress = progress
                } else {
                    binding.pbarKanjiLearned.progress = 0
                }
            }
        }

        binding.rvKanjiWords.layoutManager = LinearLayoutManager(this)
        kanjiWordAdapter = KanjiWordAdapter(
            onItemClick = {speakKanji(kanjiWord = it)},
            onBookmarkClick =  {learnedViewModel.updateBookmarkStatus(it.id,!it.is_checked)
            })
        binding.rvKanjiWords.adapter = kanjiWordAdapter

        learnedViewModel.kanjis.observe(this) { kanjiList -> // Memuat data dari sqlite
            if (kanjiList.isNullOrEmpty()) {
                binding.rvKanjiWords.visibility = View.GONE
                binding.layoutNoData.visibility = View.VISIBLE
            } else {
                binding.rvKanjiWords.visibility = View.VISIBLE
                binding.layoutNoData.visibility = View.GONE
                kanjiWordAdapter.submitList(kanjiList)
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
            val text = kanjiWord?.furigana
            if (text!!.isNotEmpty()) {
                tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.JAPANESE) // Atur bahasa ke Jepang
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, getString(R.string.bahasa_tidak_didukung), Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, getString(R.string.inisialisasi_tts_gagal), Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadAds(){
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = BuildConfig.LEARNED_ACTIVITY
        binding.adviewContainerMain.addView(adView)
        // Request
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    override fun onDestroy() {
        if (tts != null) {
            tts.stop()
            tts.shutdown()
        }
        super.onDestroy()
    }
}