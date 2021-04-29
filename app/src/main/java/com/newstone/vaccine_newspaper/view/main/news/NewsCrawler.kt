package com.newstone.vaccine_newspaper.view.main.news

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import org.jsoup.Jsoup
import java.lang.Exception
import java.net.URL

class NewsCrawler {
    val LAST_INDEX = 10

    val list = mutableListOf<NewsItem>()
    val baseUrl = "https://news.naver.com/main/list.nhn?mode=LS2D&sid2=255&sid1=102&mid=shm&date="
    val currData: String = "20210429"
    var currIndex = 1

    fun CrawlingDataFromWeb() {
        while(true) {
            val url = baseUrl + currData + "&page=" + currIndex.toString()
            val document = Jsoup.connect(url).get()

            val pagingList = document.getElementsByClass("paging").select("strong").toList().filter {
                if(it.text() == currIndex.toString())
                    true
                else
                    false
            }
            if(currIndex == LAST_INDEX || pagingList.size == 0)
                return

            val li = document.getElementsByClass("type06_headline").select("li")
            for (item in li) {
                val url = item.select("dt").select("a").get(0).attr("href")
                val imageUrl = item.select("dt").select("a").get(0).select("img").attr("src")
                var bmp: Bitmap? = null
                try {
                    bmp = BitmapFactory.decodeStream(URL(imageUrl).openConnection().getInputStream())
                } catch (e:Exception) {
                    bmp = null
                }

                var title:String
                try {
                    title = item.select("dt").select("a").get(1).text()
                } catch (e: Exception) {
                    title = "Error"
                }

                list.add(NewsItem(title, url, bmp))
            }
            currIndex++
        }
    }

}