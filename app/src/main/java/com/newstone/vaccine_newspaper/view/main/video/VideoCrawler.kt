package com.newstone.vaccine_newspaper.view.main.video

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import com.newstone.vaccine_newspaper.view.main.util.ContentFilter
import com.newstone.vaccine_newspaper.view.main.video.adapter.VideoItem
import org.jsoup.Jsoup
import java.net.URL
import java.util.*

class VideoCrawler {
    val MAXIMUM_INDEX = 20

    val list = mutableListOf<VideoItem>()
    val channelList = arrayOf("sbs8news")
    private val baseUrl = "https://www.youtube.com/"
    private var currIndex = 1

    fun CrawlingDataFromWeb() {
    }

    fun clear() {
        list.clear()
        currIndex = 1
    }
}