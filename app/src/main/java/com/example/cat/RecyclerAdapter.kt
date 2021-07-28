package com.example.cat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerAdapter(private var titles: List<String>,
                      private var summaries: List<String>,
                      private var dates: List<String>,
                      private var newsSites: List<String>):
RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemTitle: TextView = itemView.findViewById(R.id.tv_title)
        val itemSummary: TextView = itemView.findViewById(R.id.tv_summary)
        val itemDate: TextView = itemView.findViewById(R.id.tv_publishedAt)
        val itemNewsSite: TextView = itemView.findViewById(R.id.tv_newsSite)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemSummary.text = summaries[position]
        holder.itemDate.text = dates[position]
        holder.itemNewsSite.text = newsSites[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}