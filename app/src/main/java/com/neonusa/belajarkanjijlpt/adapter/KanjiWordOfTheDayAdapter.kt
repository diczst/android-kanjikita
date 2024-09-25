package com.neonusa.belajarkanjijlpt.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord
import com.neonusa.belajarkanjijlpt.utils.MyPreference

class KanjiWordOfTheDayAdapter(
    private val onItemClick: (KanjiWord) -> Unit,
    private val onBookmarkClick: (KanjiWord) -> Unit
) : ListAdapter<KanjiWord, KanjiWordOfTheDayAdapter.ViewHolder>(KanjiWordDiffCallback()) {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordTextView: TextView = itemView.findViewById(R.id.kanji_text_view_word)
        val furiganaTextView: TextView = itemView.findViewById(R.id.furigana_text_view_word)
        val romajiTextView: TextView = itemView.findViewById(R.id.romaji_text_view_word)
        val meanTextView: TextView = itemView.findViewById(R.id.mean_text_view_word)
        val checkedIcon: ImageView = itemView.findViewById(R.id.icon_checked)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kanji_word_with_romaji_no_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val kanjiWord = getItem(position)  // Access item using getItem()
        holder.wordTextView.text = kanjiWord.kanji_word
        holder.furiganaTextView.text = kanjiWord.furigana
        holder.romajiTextView.text = kanjiWord.romaji

        // Setting meaning based on language preference
        if (MyPreference.lang.equals("in")) {
            val firstWord = kanjiWord.mean_id.split(";").first().trim()
            holder.meanTextView.text = firstWord
        } else {
            val firstWord = kanjiWord.mean_en.split(";").first().trim()
            holder.meanTextView.text = firstWord
        }

        // Set checked icon based on is_checked flag
        if (kanjiWord.is_checked) {
            holder.checkedIcon.setImageResource(R.drawable.baseline_checked_circle_24)
            holder.wordTextView.setTextColor(Color.parseColor("#329900"))
            holder.furiganaTextView.visibility = View.GONE
            holder.romajiTextView.visibility = View.GONE
            holder.meanTextView.visibility = View.GONE
        } else {
            holder.checkedIcon.setImageResource(R.drawable.baseline_check_circle_outline_gray_24)
            holder.wordTextView.setTextColor(Color.parseColor("#000000"))
            holder.furiganaTextView.visibility = View.VISIBLE
            holder.romajiTextView.visibility = View.VISIBLE
            holder.meanTextView.visibility = View.VISIBLE
        }

        // Handle bookmark click
        holder.checkedIcon.setOnClickListener {
            onBookmarkClick(kanjiWord)
            notifyItemChanged(position)
        }

        // Handle item click with animation
        holder.itemView.setOnClickListener {
            it.animate()
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(100)
                .withEndAction {
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
}