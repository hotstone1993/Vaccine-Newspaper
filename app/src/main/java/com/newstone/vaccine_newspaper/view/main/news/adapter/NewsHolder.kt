package com.newstone.vaccine_newspaper.view.main.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R

class NewsHolder(context:Context, parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.item_news, parent, false)) {

    fun onBind(item: NewsData) {
        itemView.onBind(item)
    }

    private fun View.onBind(item: NewsData) {
        val news: TextView = findViewById(R.id.titleTextView)
        val newsImage: ImageView = findViewById(R.id.newsImageView)
        news.text = item.title
        //newsImage.setImageDrawable(item.image)
        newsImage.setImageResource(R.drawable.ic_item_defalut)
    }
}