package com.newstone.vaccine_newspaper.view.main.news.presenter

import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.ViewModel
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
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
            view.showProgressBar()
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            recyclerModel.notifyData()
            presenter.isLoading = true
            view.hideProgressBar()
        }
    }
}