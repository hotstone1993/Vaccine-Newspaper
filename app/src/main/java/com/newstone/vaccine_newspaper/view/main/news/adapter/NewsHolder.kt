package com.newstone.vaccine_newspaper.view.main.news.adapter

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.database.NewsDatabase
import com.newstone.vaccine_newspaper.view.main.news.database.NewsEntity
import kotlin.concurrent.thread


class NewsHolder(context:Context, parent: ViewGroup, private val newsData: MutableList<NewsItem>, private val startWebViewActivityFunction: (String, String)-> Unit) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
    R.layout.item_news, parent, false)) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun onBind(item: NewsItem) {
        itemView.onBind(item)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun View.onBind(item: NewsItem) {
        val news: TextView = findViewById(R.id.titleTextView)
        val newsImage: ImageView = findViewById(R.id.newsImageView)
        news.text = item.title
        Glide.with(this).load(Uri.parse(item.image)).placeholder(R.drawable.ic_image_error)
            .error(R.drawable.ic_image_error).fallback(R.drawable.ic_image_error).into(newsImage)
        if(item.isClicked) {
            itemView.setBackgroundColor(resources.getColor(R.color.news_after_click_item, null))
        } else {
            itemView.setBackgroundColor(resources.getColor(R.color.white, null))
        }

        itemView.setOnClickListener {
            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION) {
                startWebViewActivityFunction(newsData[pos].url, newsData[pos].title)
                newsData[pos].isClicked = true
                itemView.setBackgroundColor(resources.getColor(R.color.news_after_click_item, null))
                thread {
                    NewsDatabase.getInstance(context).newsDAO().insert(NewsEntity(newsData[pos].url, NewsRepository.getDate()))
                }
            }
        }
    }

}
