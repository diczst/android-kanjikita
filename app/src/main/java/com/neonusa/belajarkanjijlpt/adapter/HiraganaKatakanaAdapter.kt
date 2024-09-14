package com.neonusa.belajarkanjijlpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.data.model.HiraganaKatakanaItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem

class HiraganaKatakanaAdapter(private val items: List<HiraganaKatakanaItem>,
                  private val onItemClick: (KanjiItem) -> Unit) :
    RecyclerView.Adapter<HiraganaKatakanaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kanjiTextView: TextView = itemView.findViewById(R.id.text_hiragana_katakana)
        val meanTextView: TextView = itemView.findViewById(R.id.text_pronounce)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hiragana_katakana, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hiraganaKatakanaItem = items[position]
        holder.kanjiTextView.text = hiraganaKatakanaItem.letter
        holder.meanTextView.text = hiraganaKatakanaItem.pronounce
        holder.itemView.setOnClickListener {
//            onItemClick(kanjiItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}