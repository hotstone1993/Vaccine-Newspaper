package com.newstone.vaccine_newspaper.view.main.news.presenter

import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.ViewModel
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

class NewsPresenter(private val view: NewsContract.View, private val recyclerModel: BaseRecyclerModel) : ViewModel(), NewsContract.ViewModel{
    val isLoading:AtomicBoolean by lazy {
        AtomicBoolean(false)
    }

    override fun loadNews() {
        CoroutineScope(Dispatchers.Main).launch {
            isLoading.set(false)
            view.showProgressBar()

            withContext(Dispatchers.IO) {
                NewsRepository.loadData {
                    it.forEach {
                        recyclerModel.addItem(it)
                    }
                }
            }
            recyclerModel.notifyData()
            view.hideProgressBar()
            isLoading.set(true)
        }
    }
}