package com.newstone.vaccine_newspaper.view.main.video.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R

class VideoHolder(context: Context, parent: ViewGroup, val videoList: MutableList<VideoItem>) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
        R.layout.item_news, parent, false)) {

    @RequiresApi(Build.VERSION_CODES.M)
    private fun View.onBind(item: VideoItem) {
        itemView.setOnClickListener {
            val pos = adapterPosition
            if(pos != RecyclerView.NO_POSITION) {
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun onBind(item: VideoItem) {
        itemView.onBind(item)
    }
}