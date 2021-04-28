package com.newstone.vaccine_newspaper.view.main.news.data

import com.newstone.vaccine_newspaper.view.main.data.BaseDataSource
import com.newstone.vaccine_newspaper.view.main.news.NewsCrawler

class NewsData: BaseDataSource {
    val crawler: NewsCrawler by lazy {
        NewsCrawler()
    }

    override fun loadData(dataList: (List<Any>) -> Unit) {
        dataList(crawler.list)
    }
}