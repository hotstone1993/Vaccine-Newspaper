package com.newstone.vaccine_newspaper.view.main.video.adapter

import android.content.Context
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel

class VideoAdapter(val context: Context, val startPlaybackActivityFunction: (String, String)-> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), BaseRecyclerModel {
    val videoList = mutableListOf<VideoItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoHolder(context, parent, videoList, startPlaybackActivityFunction)
    }

    override fun addItem(item: Any) {
        videoList.add(item as VideoItem)
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun notifyData() {
        notifyDataSetChanged()
    }

    override fun clearData() {
        videoList.clear()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as VideoHolder).onBind(videoList[position])
    }
}