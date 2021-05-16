package com.newstone.vaccine_newspaper.view.main.video.adapter

import android.graphics.Bitmap

data class VideoItem(val url: String
                     , val preview: Bitmap?
                     , val title: String
                     , var isClicked: Boolean
                     , val date: String
                     , val channelName: String
                     , val views: String
                     , val time: String
                     , val channelIcon: Bitmap?)