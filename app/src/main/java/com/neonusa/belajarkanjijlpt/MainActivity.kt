package com.neonusa.belajarkanjijlpt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var adapter: GridAdapter
    private lateinit var subItemAdapter: SubItemAdapter
    private val data = listOf("DATA A", "DATA B", "DATA C", "DATA D", "DATA E", "DATA F", "DATA G", "DATA H", "DATA I")
    private var currentPage = 0
    private val itemsPerPage = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)

        binding.prevButton.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                updateRecyclerView()
            }
        }

        binding.nextButton.setOnClickListener {
            if ((currentPage + 1) * itemsPerPage < data.size) {
                currentPage++
                updateRecyclerView()
            }
        }

        // Initialize RecyclerView with the first page
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val start = currentPage * itemsPerPage
        val end = minOf(start + itemsPerPage, data.size)
        val pageData = data.subList(start, end)

        adapter = GridAdapter(pageData) {
            showSubItems(it)
        }
        binding.recyclerview.adapter = adapter

        binding.pageTitle.text = "Halaman ${currentPage + 1}"
    }

    private fun showSubItems(item: String) {
        val subItems = when (item) {
            "DATA A" -> listOf("SubItem A1", "SubItem A2", "SubItem A3")
            "DATA B" -> listOf("SubItem B1", "SubItem B2", "SubItem B3")
            "DATA C" -> listOf("SubItem C1", "SubItem C2", "SubItem C3")
            // Tambahkan sub-item untuk item lainnya sesuai kebutuhan
            else -> emptyList()
        }

        subItemAdapter = SubItemAdapter(subItems)
        binding.recyclerviewSubitem.adapter = subItemAdapter
    }
}