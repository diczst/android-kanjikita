package com.neonusa.belajarkanjijlpt.ui.search

import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.neonusa.belajarkanjijlpt.BuildConfig
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.databinding.ActivitySearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class SearchActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivitySearchBinding
    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var kanjiWordAdapter: KanjiWordAdapter
    private lateinit var tts: TextToSpeech
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)
        mediaPlayer = MediaPlayer.create(this, R.raw.learned)
        loadAds()

        binding.rvWord.layoutManager = LinearLayoutManager(this)
        kanjiWordAdapter = KanjiWordAdapter(
            onItemClick = {speakKanji(kanjiWord = it)},
            onBookmarkClick = {
                if(!it.is_checked){
                    playSound()
                    speakKanji(kanjiWord = it)
                }
                searchViewModel.updateBookmarkStatus(it.id, !it.is_checked)
            }
        )
        binding.rvWord.adapter = kanjiWordAdapter

        // Observasi hasil pencarian
        searchViewModel.searchResults.observe(this) { results ->
            if (results.isNotEmpty()) {
                kanjiWordAdapter.submitList(results)
                binding.layoutNoData.visibility = View.GONE
            } else {
                kanjiWordAdapter.submitList(null)
                binding.layoutNoData.visibility = View.VISIBLE
            }
        }

        setupSearch()
    }

    private fun setupSearch() {
        binding.edtSearchKanji.addTextChangedListener { text ->
            if (text != null) {
                searchViewModel.searchKanji(text.toString())  // Panggil searchKanji di ViewModel
                // agar kanjiWord scroll ke atas saat menekan kanjiCard
                kanjiWordAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                        super.onItemRangeInserted(positionStart, itemCount)
                        binding.rvWord.scrollToPosition(0)
                    }
                })
            }
        }

        // Tombol kembali untuk menutup pencarian
        binding.ivSearchBack.setOnClickListener {
            finish()
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
    private fun speakKanji(kanjiItem: KanjiItem? = null, kanjiWord: KanjiWord? = null) {
        val text = kanjiItem?.kanji ?: kanjiWord?.furigana
        if (!text.isNullOrEmpty()) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun loadAds(){
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = BuildConfig.SEARCH_ACTIVITY
        binding.adviewContainerMain.addView(adView)
        // Request
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
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