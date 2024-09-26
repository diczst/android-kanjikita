package com.neonusa.belajarkanjijlpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem
import com.neonusa.belajarkanjijlpt.utils.MyPreference
import java.util.Locale

class GridAdapter(private val items: List<KanjiItem>,
                  private val onItemClick: (KanjiItem) -> Unit) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kanjiTextView: TextView = itemView.findViewById(R.id.kanji_text_view)
        val meanTextView: TextView = itemView.findViewById(R.id.mean_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kanji_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kanjiItem = items[position]
        holder.kanjiTextView.text = kanjiItem.kanji

        val defaultLocale = Locale.getDefault()
        if (defaultLocale.language.equals("in")) {
            holder.meanTextView.text = kanjiItem.mean_id
        } else {
            holder.meanTextView.text = kanjiItem.mean_en
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
            onItemClick(kanjiItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}