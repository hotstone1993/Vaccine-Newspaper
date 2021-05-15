package com.newstone.vaccine_newspaper.view.main.video.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R

class VideoHolder(context: Context, parent: ViewGroup, val videoList: MutableList<VideoItem>) : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(
        R.layout.item_video, parent, false)) {

    @RequiresApi(Build.VERSION_CODES.M)
    private fun View.onBind(item: VideoItem) {
        val preview = itemView.findViewById<ImageView>(R.id.videoPreview)
        val videoPreviewProgressBar = itemView.findViewById<ProgressBar>(R.id.videoPreviewProgressBar)
        val videoTimeTextView = itemView.findViewById<TextView>(R.id.videoTimeTextView)

        val titleTextView = itemView.findViewById<TextView>(R.id.videoTitleTextView)

        val channelImage = itemView.findViewById<ImageView>(R.id.channelImage)
        val channelNameTextView = itemView.findViewById<TextView>(R.id.channelNameTextView)
        val viewsTextView = itemView.findViewById<TextView>(R.id.viewsTextView)
        val dateTextView = itemView.findViewById<TextView>(R.id.dateTextView)

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