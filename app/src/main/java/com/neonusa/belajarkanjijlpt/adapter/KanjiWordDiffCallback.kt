package com.neonusa.belajarkanjijlpt.adapter

import androidx.recyclerview.widget.DiffUtil
import com.neonusa.belajarkanjijlpt.data.model.KanjiWord

class KanjiWordDiffCallback : DiffUtil.ItemCallback<KanjiWord>() {
    override fun areItemsTheSame(oldItem: KanjiWord, newItem: KanjiWord): Boolean {
        // Compare unique identifiers, assuming 'id' is a unique field in KanjiWord
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: KanjiWord, newItem: KanjiWord): Boolean {
        // Compare all fields or use equals if KanjiWord has implemented it
        return oldItem == newItem
    }
}

