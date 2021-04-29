package com.newstone.vaccine_newspaper.view.main.news.presenter

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository

class NewsPresenter(private val view: NewsContract.View, private val recyclerModel: BaseRecyclerModel) : ViewModel(), NewsContract.ViewModel{
    var isLoading = false

    override fun loadNews() {
        NewsAsyncTask(this, view, recyclerModel).execute()
    }

    class NewsAsyncTask(private val presenter :NewsPresenter,
                        private val view: NewsContract.View, private val recyclerModel: BaseRecyclerModel
    ) : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            NewsRepository.loadData {
                it.forEach {
                    recyclerModel.addItem(it)
                }
            }
        }

        override fun onPreExecute() {
            super.onPreExecute()
            presenter.isLoading = false
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            recyclerModel.notifyData()
            presenter.isLoading = true
        }
    }
}