package com.neonusa.belajarkanjijlpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.model.KanjiSubitem

class SubItemAdapter(private val data: List<KanjiSubitem>) : RecyclerView.Adapter<SubItemAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.kanji_text_view_word)
        val furiganaTextView: TextView = itemView.findViewById(R.id.furigana_text_view_word)
        val romajiTextView: TextView = itemView.findViewById(R.id.romaji_text_view_word)
        val meanTextView: TextView = itemView.findViewById(R.id.mean_text_view_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kanji_word_with_romaji, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subItem = data[position]
        holder.wordTextView.text = subItem.kanji_word
        holder.furiganaTextView.text = subItem.furigana
        holder.romajiTextView.text = subItem.romaji
        holder.meanTextView.text = subItem.mean
    }

    override fun getItemCount(): Int = data.size
}