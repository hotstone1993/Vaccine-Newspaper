package com.newstone.vaccine_newspaper.view.main.news.data

import com.newstone.vaccine_newspaper.view.main.data.BaseDataSource

object NewsRepository : BaseDataSource{
    private val newsData: NewsData by lazy {
        NewsData()
    }

    override fun loadData(dataList: (List<Any>) -> Unit) {
        newsData.loadData(dataList)
    }
}