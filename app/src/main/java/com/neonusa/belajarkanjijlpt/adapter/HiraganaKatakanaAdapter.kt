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
                  private val onItemClick: (HiraganaKatakanaItem) -> Unit) :
    RecyclerView.Adapter<HiraganaKatakanaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letterTextView: TextView = itemView.findViewById(R.id.text_hiragana_katakana)
        val meanTextView: TextView = itemView.findViewById(R.id.text_pronounce)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_hiragana_katakana, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hiraganaKatakanaItem = items[position]

        if (hiraganaKatakanaItem.letter.isNotEmpty()) {
            holder.letterTextView.text = hiraganaKatakanaItem.letter
            holder.meanTextView.text = hiraganaKatakanaItem.pronounce
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.isEnabled = true

        } else {
            holder.itemView.visibility = View.GONE
            holder.itemView.isEnabled = false
        }

        holder.itemView.setOnClickListener {
            // animasi saat diklik
            // Animasi untuk memperbesar item saat diklik
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

            onItemClick(hiraganaKatakanaItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}