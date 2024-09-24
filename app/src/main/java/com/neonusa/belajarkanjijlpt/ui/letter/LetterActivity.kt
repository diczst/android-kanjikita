package com.neonusa.belajarkanjijlpt.ui.letter

import android.annotation.SuppressLint
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.HiraganaKatakanaAdapter
import com.neonusa.belajarkanjijlpt.data.model.HiraganaKatakanaItem
import com.neonusa.belajarkanjijlpt.databinding.ActivityLetterBinding
import com.neonusa.belajarkanjijlpt.utils.hiraganaCombinationGenerator
import com.neonusa.belajarkanjijlpt.utils.hiraganaDakuonGenerator
import com.neonusa.belajarkanjijlpt.utils.hiraganaGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaCombinationGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaDakuonGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaGenerator
import java.util.Locale

class LetterActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private lateinit var binding: ActivityLetterBinding

    private lateinit var letterType: String
    private lateinit var actionBarTitle: String
    private lateinit var desc: String

    private lateinit var hirakataItems: List<HiraganaKatakanaItem>
    private lateinit var dakuonHirakataItems: List<HiraganaKatakanaItem>
    private lateinit var combinationHirakataItems: List<HiraganaKatakanaItem>

    private lateinit var tts: TextToSpeech

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tts = TextToSpeech(this, this)

        letterType = intent.getStringExtra(LETTER_TYPE)!!

        if(letterType == HIRAGANA) {
            actionBarTitle = "Hiragana"
            desc = getString(R.string.hiragana_desc, actionBarTitle)
            hirakataItems = hiraganaGenerator()
            dakuonHirakataItems = hiraganaDakuonGenerator()
            combinationHirakataItems = hiraganaCombinationGenerator()
        } else {
            actionBarTitle = "Katakana"
            desc = getString(R.string.katakana_desc, actionBarTitle)
            hirakataItems = katakanaGenerator()
            dakuonHirakataItems = katakanaDakuonGenerator()
            combinationHirakataItems = katakanaCombinationGenerator()
        }

        setSupportActionBar(binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.tvToolbarTitleLetter.text = actionBarTitle
        binding.tvDescHirakata.text = desc

        val hirakataAdapter = HiraganaKatakanaAdapter(hirakataItems){speakLetter(it)}
        val dakuonHirakataAdapter = HiraganaKatakanaAdapter(dakuonHirakataItems){speakLetter(it)}
        val combinationHirakataAdapter = HiraganaKatakanaAdapter(combinationHirakataItems){speakLetter(it)}

        binding.rvHirakata.layoutManager = GridLayoutManager(this, 5)
        binding.rvHirakata.adapter = hirakataAdapter

        binding.rvDakuon.layoutManager = GridLayoutManager(this,5)
        binding.rvDakuon.adapter = dakuonHirakataAdapter

        binding.rvCombination.layoutManager = GridLayoutManager(this,3)
        binding.rvCombination.adapter = combinationHirakataAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    // Fungsi untuk memutar suara TTS
    private fun speakLetter(hiraganaKatakanaItem: HiraganaKatakanaItem) {
        val text = hiraganaKatakanaItem.letter
        if (text.isNotEmpty()) {
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

    companion object {
        const val LETTER_TYPE = "LETTER_TYPE"
        const val HIRAGANA = "HIRAGANA"
        const val KATAKANA = "KATAKANA"
    }
}