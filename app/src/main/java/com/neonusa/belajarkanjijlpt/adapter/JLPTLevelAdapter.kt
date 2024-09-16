package com.neonusa.belajarkanjijlpt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neonusa.belajarkanjijlpt.R
import com.neonusa.belajarkanjijlpt.data.model.JLPTLevelItem
import com.neonusa.belajarkanjijlpt.data.model.KanjiItem

class JLPTLevelAdapter(private val data: List<JLPTLevelItem>,
                       private val onItemClick: (JLPTLevelItem) -> Unit) : RecyclerView.Adapter<JLPTLevelAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val levelTextView: TextView = itemView.findViewById(R.id.jlpt_level_text_view)
        val levelImage: ImageView = itemView.findViewById(R.id.jlpt_level_image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_jlpt_level, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.levelTextView.text = item.level
        holder.levelImage.setImageResource(item.imageResId)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = data.size
}

