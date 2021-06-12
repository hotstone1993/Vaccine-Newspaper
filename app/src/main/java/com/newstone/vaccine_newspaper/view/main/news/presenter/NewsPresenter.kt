package com.newstone.vaccine_newspaper.view.main.news.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.database.NewsDatabase
import com.newstone.vaccine_newspaper.view.main.news.database.NewsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

class NewsPresenter(val context: Context, private val view: NewsContract.View, private val recyclerModel: BaseRecyclerModel) : ViewModel(), NewsContract.ViewModel{
    val isLoading:AtomicBoolean by lazy {
        AtomicBoolean(false)
    }

    fun MutableList<NewsEntity>.check(url: String): Boolean {
        for(entity in this) {
            if(entity.url == url) {
                this.remove(entity)
                return true
            }
        }
        return false
    }

    override fun loadNews() {
        if(NewsRepository.isLoaded)
            return

        CoroutineScope(Dispatchers.Main).launch {
            isLoading.set(false)
            view.showProgressBar()
            val newsDao = NewsDatabase.getInstance(context).newsDAO()

            withContext(Dispatchers.IO) {
                val result = newsDao.getAllNews(NewsRepository.getDate())
                NewsRepository.loadData {
                    it.forEach {
                        if(result.check((it as NewsItem).url)) {
                            it.isClicked = true
                        }
                        recyclerModel.addItem(it)
                    }
                }
            }
            recyclerModel.notifyData()
            view.hideProgressBar()
            isLoading.set(true)
            NewsRepository.isLoaded = true
        }
    }
}