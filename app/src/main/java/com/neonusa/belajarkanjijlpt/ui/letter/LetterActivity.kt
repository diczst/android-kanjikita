package com.neonusa.belajarkanjijlpt.ui.letter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.HiraganaKatakanaAdapter
import com.neonusa.belajarkanjijlpt.data.model.HiraganaKatakanaItem
import com.neonusa.belajarkanjijlpt.databinding.ActivityLetterBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.utils.hiraganaCombinationGenerator
import com.neonusa.belajarkanjijlpt.utils.hiraganaDakuonGenerator
import com.neonusa.belajarkanjijlpt.utils.hiraganaGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaCombinationGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaDakuonGenerator
import com.neonusa.belajarkanjijlpt.utils.katakanaGenerator

class LetterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLetterBinding

    private lateinit var letterType: String
    private lateinit var actionBarTitle: String
    private lateinit var desc: String

    private lateinit var hirakataItems: List<HiraganaKatakanaItem>
    private lateinit var dakuonHirakataItems: List<HiraganaKatakanaItem>
    private lateinit var combinationHirakataItems: List<HiraganaKatakanaItem>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        letterType = intent.getStringExtra(LETTER_TYPE)!!

        if(letterType == HIRAGANA) {
            actionBarTitle = "Hiragana"
            desc = "$actionBarTitle adalah salah satu dari tiga sistem penulisan utama dalam bahasa Jepang, selain katakana dan kanji"
            hirakataItems = hiraganaGenerator()
            dakuonHirakataItems = hiraganaDakuonGenerator()
            combinationHirakataItems = hiraganaCombinationGenerator()
        } else {
            actionBarTitle = "Katakana"
            desc = "$actionBarTitle adalah salah satu dari tiga sistem penulisan utama dalam bahasa Jepang, selain hiragana dan kanji"
            hirakataItems = katakanaGenerator()
            dakuonHirakataItems = katakanaDakuonGenerator()
            combinationHirakataItems = katakanaCombinationGenerator()
        }

        setSupportActionBar(binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.tvToolbarTitleLetter.text = actionBarTitle
        binding.tvDescHirakata.text = desc

        val hirakataAdapter = HiraganaKatakanaAdapter(hirakataItems){}
        val dakuonHirakataAdapter = HiraganaKatakanaAdapter(dakuonHirakataItems){}
        val combinationHirakataAdapter = HiraganaKatakanaAdapter(combinationHirakataItems){}

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

    companion object {
        const val LETTER_TYPE = "LETTER_TYPE"
        const val HIRAGANA = "HIRAGANA"
        const val KATAKANA = "KATAKANA"
    }
}