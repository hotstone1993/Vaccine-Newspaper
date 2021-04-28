package com.newstone.vaccine_newspaper.view.main.news.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel

class NewsAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BaseRecyclerModel {
    private val newsList = mutableListOf<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsHolder(context, parent)
    }

    override fun addItem(item: Any) {
        when {
            item is NewsItem -> {
                newsList.add(item)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    override fun notifyDataSetChang() {
        notifyDataSetChang()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NewsHolder)?.onBind(newsList[position])
    }
}