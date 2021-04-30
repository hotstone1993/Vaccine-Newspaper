package com.newstone.vaccine_newspaper.view.main.news.presenter

class NewsContract {

    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface ViewModel {
        fun loadNews()
    }
}