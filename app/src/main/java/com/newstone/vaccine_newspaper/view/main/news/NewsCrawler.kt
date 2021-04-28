package com.newstone.vaccine_newspaper.view.main.news

import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import org.jsoup.nodes.Document

class NewsCrawler {
    val list = mutableListOf<NewsItem>()
    val baseUrl = "https://news.naver.com/main/list.nhn?mode=LS2D&sid2=255&sid1=102&mid=shm&date=20210401"
}