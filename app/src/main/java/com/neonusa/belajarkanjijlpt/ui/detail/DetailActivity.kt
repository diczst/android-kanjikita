package com.neonusa.belajarkanjijlpt.ui.detail

import DetailViewModel
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neonusa.belajarkanjijlpt.BuildConfig
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.GridAdapter
import com.neonusa.belajarkanjijlpt.adapter.KanjiWordAdapter
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.databinding.ActivityDetailBinding
import com.neonusa.belajarkanjijlpt.utils.loadJSONFromAssets
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class DetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var tts: TextToSpeech
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var jlptLevel: String
    private val detailViewModel: DetailViewModel by viewModel()
    private var mInterstitialAd: InterstitialAd? = null

    // KANJI
    //==============================================================
    private var jsonKanjiSingleString: String? = null
    private lateinit var kanjiSingleItems: List<KanjiItem>
    private lateinit var kanjiSingleItemsFilteredByLevel: List<KanjiItem>
    private var currentPage = 0
    private val itemsPerPage = 9
    //==============================================================

    private lateinit var kanjiWordAdapter: KanjiWordAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mediaPlayer = MediaPlayer.create(this, R.raw.learned)

        loadAds()
        interstitialAdsSetup()

        tts = TextToSpeech(this, this)
        jlptLevel = intent.getStringExtra(JLPT_LEVEL)!!

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbarTitleDetail.text = "JLPT $jlptLevel"

        binding.prevButton.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                val start = currentPage * itemsPerPage
                val end = minOf(start + itemsPerPage, kanjiSingleItemsFilteredByLevel.size)
                val pageData = kanjiSingleItemsFilteredByLevel.subList(start, end)
                loadKanjiData()
                loadKanjiWordData(pageData.first())
            }
        }

        binding.nextButton.setOnClickListener {
            if ((currentPage + 1) * itemsPerPage < kanjiSingleItemsFilteredByLevel.size) {
                currentPage++

                val start = currentPage * itemsPerPage
                val end = minOf(start + itemsPerPage, kanjiSingleItemsFilteredByLevel.size)
                val pageData = kanjiSingleItemsFilteredByLevel.subList(start, end)
                loadKanjiData()
                loadKanjiWordData(pageData.first())
            }
        }

        jsonKanjiSingleString = loadJSONFromAssets("kanji_single.json", this) // Parse JSON string
        val kanjiListType = object : TypeToken<List<KanjiItem>>() {}.type
        kanjiSingleItems = Gson().fromJson(jsonKanjiSingleString, kanjiListType) // Parse string json to list
        kanjiSingleItemsFilteredByLevel = kanjiSingleItems.filter { it.level!! == jlptLevel } // filter list
        binding.rvSingleKanji.layoutManager = GridLayoutManager(this, 3)

        //==============================================
        binding.rvKanjiWords.layoutManager = LinearLayoutManager(this)
        kanjiWordAdapter = KanjiWordAdapter(
            onItemClick = {speakKanji(kanjiWord = it)},
            onBookmarkClick =  {
                if(!it.is_checked){
                    playSound()
                    speakKanji(kanjiWord = it)
                }
                detailViewModel.updateBookmarkStatus(it.id,!it.is_checked)
            })
        binding.rvKanjiWords.adapter = kanjiWordAdapter

        detailViewModel.kanjis.observe(this) { kanjiList -> // Memuat data dari sqlite
            val firstKanji = kanjiSingleItemsFilteredByLevel.first()
            val filteredKanjiWordItems = kanjiList.filter { it.kanji_word.contains(
                firstKanji.kanji.toString())}
            kanjiWordAdapter.submitList(filteredKanjiWordItems)
            binding.layoutShimmerRvKanjiWord.root.stopShimmer()
            binding.layoutShimmerRvKanjiWord.root.visibility = View.GONE
            binding.rvKanjiWords.visibility = View.VISIBLE
        // Log.d(this::class.simpleName, "onCreate: $kanjiList")
        }
        //==============================================
        loadKanjiData()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showInterstial()
                if(mInterstitialAd != null){
                    mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            mInterstitialAd = null
                            finish()
                        }
                    }
                } else {
                    // in case no ads
                    mInterstitialAd = null
                    finish()
                }
            }
        })

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

    override fun onSupportNavigateUp(): Boolean {
        showInterstial()
        if(mInterstitialAd != null){
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    finish()
                }
            }
        } else {
            // in case no ads
            mInterstitialAd = null
            finish()
        }
        return super.onSupportNavigateUp()
    }

    private fun loadKanjiWordData(kanjiSingle: KanjiItem){
        detailViewModel.kanjis.observe(this) { kanjiList -> // Memuat data dari sqlite
            val filteredKanjiWordItems = kanjiList.filter { it.kanji_word.contains(
                kanjiSingle.kanji.toString())}
            kanjiWordAdapter.submitList(filteredKanjiWordItems)

            // agar kanjiWord scroll ke atas saat menekan kanjiCard
            kanjiWordAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    binding.rvKanjiWords.scrollToPosition(0)
                }
            })
            // Log.d(this::class.simpleName, "onCreate: $kanjiList")
        }
    }

    private fun loadKanjiData(){
        val start = currentPage * itemsPerPage

        if (jsonKanjiSingleString != null) {
                val end = minOf(start + itemsPerPage, kanjiSingleItemsFilteredByLevel.size)
            val pageData = kanjiSingleItemsFilteredByLevel.subList(start, end)
            val kanjiAdapter = GridAdapter(pageData){
                speakKanji(kanjiItem = it)
                loadKanjiWordData(it)
            }
            binding.rvSingleKanji.adapter = kanjiAdapter
        }
        binding.pageTitle.text = getString(R.string.page_title, currentPage + 1)
    }

    private fun loadAds(){
        val adView = AdView(this)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = BuildConfig.DETAIL_ACTIVITY
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

    private fun interstitialAdsSetup(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,
            BuildConfig.DETAIL_ACTIVITY_INTERSTITIAL,
            adRequest, object : InterstitialAdLoadCallback() {

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })
    }

    private fun showInterstial(){
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(this)
        }
    }
}