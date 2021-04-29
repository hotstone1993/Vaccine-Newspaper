package com.newstone.vaccine_newspaper.view.main.news

import android.graphics.BitmapFactory
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import org.jsoup.Jsoup
import java.net.URL

class NewsCrawler {
    val list = mutableListOf<NewsItem>()
    val baseUrl = "https://news.naver.com/main/list.nhn?mode=LS2D&sid2=255&sid1=102&mid=shm&date="
    val curr: String = ""

    fun CrawlingDataFromWeb() {
        Jsoup.connect(baseUrl+curr).get().let {document ->
            val li = document.getElementsByClass("type06_headline").select("li")
            for(item in li) {
                val url = item.select("dt").select("a").get(0).attr("href")
                val imageUrl = item.select("dt").select("a").get(0).select("img").attr("src")
                val bmp = BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream())
                val title = item.select("dt").select("a").get(1).text()
                list.add(NewsItem(title, url, bmp))
            }
        }
    }

}