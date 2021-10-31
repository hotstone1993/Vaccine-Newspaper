package com.newstone.vaccine_newspaper.view.main.news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.database.NewsDatabase
import com.newstone.vaccine_newspaper.view.main.news.database.NewsEntity
import com.newstone.vaccine_newspaper.view.main.util.ViewEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsViewModel(application: Application, private val recyclerModel: BaseRecyclerModel) : AndroidViewModel(application),
    ViewEvent {
    companion object {
        val RELOAD_EVENT = "click_event"
        val ON_REFRESH_EVENT = "refresh_event"
    }

    private var _loadingStatus = MutableLiveData<Boolean>()

    val loadingStatus:LiveData<Boolean>
        get() = _loadingStatus

    fun MutableList<NewsEntity>.check(url: String): Boolean {
        for(entity in this) {
            if(entity.url == url) {
                this.remove(entity)
                return true
            }
        }
        return false
    }

    fun loadNews(force: Boolean) {
        if(NewsRepository.isLoaded && !force)
            return

        NewsRepository.resetData()
        recyclerModel.clearData()
        recyclerModel.notifyData()

        CoroutineScope(Dispatchers.Main).launch {
            showProgressBar()
            val newsDao = NewsDatabase.getInstance(getApplication<Application>().applicationContext).newsDAO()

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
            NewsRepository.isLoaded = true
            hideProgressBar()
        }
    }

    override fun excuteEvent(event: String, vararg args: Any) {
        when(event) {
            RELOAD_EVENT -> {
                loadNews(args[0] as Boolean)
            }
            ON_REFRESH_EVENT -> {
                showProgressBar()
                loadNews(args[0] as Boolean)
                hideProgressBar()
            }
            else -> {
            }
        }
    }

    fun showProgressBar() {
        _loadingStatus.value = true
    }

    fun hideProgressBar() {
        _loadingStatus.value = false
    }
}