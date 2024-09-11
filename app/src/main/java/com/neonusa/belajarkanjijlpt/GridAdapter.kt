package com.neonusa.belajarkanjijlpt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.model.KanjiItem

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
        holder.meanTextView.text = kanjiItem.mean
        holder.itemView.setOnClickListener {
            onItemClick(kanjiItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}