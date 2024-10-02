package com.neonusa.belajarkanjijlpt.ui.kanjioftheday

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.neonusa.belajarkanjijlpt.databinding.ActivityKanjiOfTheDayBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.inject
import java.util.Locale

class KanjiOfTheDayActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityKanjiOfTheDayBinding
    private val kanjiOfTheDayViewModel: KanjiOfTheDayViewModel by viewModel()
    private lateinit var tts: TextToSpeech
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var kanjiWordOfTheDayAdapter: KanjiWordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKanjiOfTheDayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        mediaPlayer = MediaPlayer.create(this, R.raw.learned)
        loadAds()

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.rvKanjiWords.layoutManager = LinearLayoutManager(this)
        kanjiWordOfTheDayAdapter = KanjiWordAdapter(
            onItemClick = {speakKanji(kanjiWord = it)},
            onBookmarkClick =  {
                if(!it.is_checked){
                    playSound()
                    speakKanji(kanjiWord = it)
                }
                kanjiOfTheDayViewModel.updateBookmarkStatus(it.id,!it.is_checked)
            })
        binding.rvKanjiWords.adapter = kanjiWordOfTheDayAdapter

        kanjiOfTheDayViewModel.getRandomKanjisOfTheDay { kanjiList->
            kanjiWordOfTheDayAdapter.submitList(kanjiList) // UI will update automatically
        }

        kanjiOfTheDayViewModel.getCheckedKanjiOfTheDayCount().observe(this){ learnedKanjiOfTheDayCount ->
            binding.tvLearnedKanji.text = learnedKanjiOfTheDayCount.toString()
            val progress = (learnedKanjiOfTheDayCount * 100) / 10
            binding.pbarKanjiLearned.progress = progress
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // Coba atur bahasa ke Jepang
            val result = tts.setLanguage(Locale.JAPANESE)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Jika Jepang tidak didukung, atur ke bahasa default perangkat
                val defaultLocale = Locale.getDefault()  // Mengambil bahasa bawaan perangkat
                val defaultResult = tts.setLanguage(defaultLocale)
                if (defaultResult == TextToSpeech.LANG_MISSING_DATA || defaultResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts.setLanguage(Locale.ENGLISH)
//                    Toast.makeText(this, getString(R.string.bahasa_tidak_didukung), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.inisialisasi_tts_gagal), Toast.LENGTH_SHORT).show()
        }
    }

    private fun playSound(){
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
            // Inisialisasi ulang MediaPlayer setelah release
            mediaPlayer = MediaPlayer.create(this, R.raw.learned)
        }

        // Mulai pemutaran setelah memvalidasi ulang
        mediaPlayer.start()
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

    private fun loadAds(){
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = BuildConfig.KANJI_OF_THE_DAY_ACTIVITY
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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}