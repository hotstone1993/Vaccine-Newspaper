package com.newstone.vaccine_newspaper.view.main.news

import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import com.newstone.vaccine_newspaper.view.main.util.ContentFilter
import org.jsoup.Jsoup
import java.text.SimpleDateFormat
import java.util.*

class NewsCrawler {
    val MAXIMUM_INDEX = 20

    val list = mutableListOf<NewsItem>()
    var currDate: String = SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(Calendar.getInstance().time)
    private val baseUrl = "https://news.naver.com/main/list.nhn?mode=LS2D&sid2=255&sid1=102&mid=shm&date="
    private var currIndex = 1

    fun CrawlingDataFromWeb() {
        while(true) {
            val url = baseUrl + currDate + "&page=" + currIndex.toString()
            val document = Jsoup.connect(url).get()

            val pagingList = document.getElementsByClass("paging").select("strong").toList().filter {
                if(it.text() == currIndex.toString())
                    true
                else
                    false
            }
            if(currIndex == MAXIMUM_INDEX || pagingList.size == 0)
                return

            val li = document.getElementsByClass("type06_headline").select("li")
            for (item in li) {
                val url = item.select("dt").select("a").get(0).attr("href")
                val imageUrl = item.select("dt").select("a").get(0).select("img").attr("src")

                try {
                    val title = item.select("dt").select("a").get(1).text()
                    if(ContentFilter.getInstance().check(title)) {
                        list.add(NewsItem(title, url, imageUrl))
                    }
                } catch (e: Exception) {
                    continue
                }
            }
            currIndex++
        }
    }

    fun clear() {
        list.clear()
        currIndex = 1
    }
}