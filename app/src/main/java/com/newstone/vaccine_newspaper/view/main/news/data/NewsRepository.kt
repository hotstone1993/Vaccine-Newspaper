package com.newstone.vaccine_newspaper.view.main.news.data

import com.newstone.vaccine_newspaper.view.main.data.BaseDataSource

object NewsRepository : BaseDataSource{
    var isLoaded = false

    private val newsData: NewsData by lazy {
        NewsData()
    }

    override fun loadData(dataList: (List<Any>) -> Unit) {
        newsData.loadData(dataList)
    }
    fun resetData() {
        isLoaded = false
        newsData.crawler.list.clear()
        newsData.crawler.clear()
    }
}