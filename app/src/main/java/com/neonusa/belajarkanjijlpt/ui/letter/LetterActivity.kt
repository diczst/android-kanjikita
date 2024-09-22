package com.neonusa.belajarkanjijlpt.ui.letter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.adapter.HiraganaKatakanaAdapter
import com.neonusa.belajarkanjijlpt.databinding.ActivityLetterBinding
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding
import com.neonusa.belajarkanjijlpt.utils.hiraganaGenerator

class LetterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLetterBinding
    private val hiraganaItems = hiraganaGenerator()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLetterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        this.supportActionBar?.title = "Hiragana"

        val hiraganaAdapter = HiraganaKatakanaAdapter(hiraganaItems){}
        binding.rvHirakata.layoutManager = GridLayoutManager(this, 5)
        binding.rvHirakata.adapter = hiraganaAdapter
    }
}