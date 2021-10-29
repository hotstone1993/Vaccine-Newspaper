package com.newstone.vaccine_newspaper.view.main.news.viewmodel

class NewsContract {
    interface ViewModel {
        fun loadNews(force: Boolean)
        fun showProgressBar()
        fun hideProgressBar()
    }
}