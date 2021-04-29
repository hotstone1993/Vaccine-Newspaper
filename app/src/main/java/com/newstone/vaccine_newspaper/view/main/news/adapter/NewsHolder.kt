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

    fun onBind(item: NewsItem) {
        itemView.onBind(item)
    }

    private fun View.onBind(item: NewsItem) {
        val news: TextView = findViewById(R.id.titleTextView)
        val newsImage: ImageView = findViewById(R.id.newsImageView)
        news.text = item.title
        newsImage.setImageBitmap(item.image)
    }
}
