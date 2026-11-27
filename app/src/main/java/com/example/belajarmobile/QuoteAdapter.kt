package com.example.belajarmobile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class QuoteAdapter(
    private val quotes: List<Quote>
) : RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvKutipan: TextView = itemView.findViewById(R.id.tvKutipan)
        val tvPenulis: TextView = itemView.findViewById(R.id.tvPenulis)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quote, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quoteItem = quotes[position]
        holder.tvKutipan.text = "\u201C${quoteItem.quote}\u201D"
        holder.tvPenulis.text = "\u2014 ${quoteItem.author}"
    }

    override fun getItemCount(): Int = quotes.size
}