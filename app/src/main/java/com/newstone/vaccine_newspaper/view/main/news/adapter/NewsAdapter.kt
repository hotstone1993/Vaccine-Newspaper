package com.newstone.vaccine_newspaper.view.main.news.adapter

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel

class NewsAdapter(private val context: Context, private val startWebViewActivityFunction: (String, String)-> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    BaseRecyclerModel {
    private val newsList = mutableListOf<NewsItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsHolder(context, parent, newsList, startWebViewActivityFunction)
    }

    override fun addItem(item: Any) {
        when {
            item is NewsItem -> {
                newsList.add(item)
            }
        }
    }

    override fun getItemCount(): Int = newsList.size

    override fun notifyData() {
        notifyDataSetChanged()
    }

    override fun clearData() {
        newsList.clear()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? NewsHolder)?.onBind(newsList[position])
    }
}