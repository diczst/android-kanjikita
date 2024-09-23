package com.neonusa.belajarkanjijlpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.utils.MyPreference

class KanjiWordAdapter(private val data: List<KanjiWord>,
                       private val onItemClick: (KanjiWord) -> Unit) : RecyclerView.Adapter<KanjiWordAdapter.ViewHolder>() {
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
        val kanjiWord = data[position]
        holder.wordTextView.text = kanjiWord.kanji_word
        holder.furiganaTextView.text = kanjiWord.furigana
        holder.romajiTextView.text = kanjiWord.romaji

        if(MyPreference.lang.equals("in")){
            val firstWord = kanjiWord.mean_id.split(";").first().trim()
            holder.meanTextView.text = firstWord
        } else {
            val firstWord = kanjiWord.mean_en.split(";").first().trim()
            holder.meanTextView.text = firstWord
        }

        holder.itemView.setOnClickListener {
            it.animate()
                .scaleX(1.1f)  // Perbesar skala X
                .scaleY(1.1f)  // Perbesar skala Y
                .setDuration(100)  // Durasi animasi
                .withEndAction {
                    // Kembalikan ke ukuran semula
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                        .start()
                }
                .start()
            onItemClick(kanjiWord)
        }

    }

    override fun getItemCount(): Int = data.size
}