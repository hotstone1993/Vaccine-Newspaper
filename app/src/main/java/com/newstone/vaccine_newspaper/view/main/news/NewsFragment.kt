package com.newstone.vaccine_newspaper.view.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsAdapter
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsItem
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsContract
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsPresenter

class NewsFragment: Fragment(), NewsContract.View {
    private val present: NewsPresenter by viewModels {
        NewsPresenterFactory(this@NewsFragment, newsRecyclerAdapter)
    }
    private val newsRecyclerAdapter: NewsAdapter by lazy {
        NewsAdapter(requireContext())
    }
    lateinit var loadingProgressBar: ProgressBar
    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // UI
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        hideProgressBar()
        newsRecyclerView = view.findViewById(R.id.newsRecyclerView)
        newsRecyclerView.run {
            adapter = newsRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        // Crawling
        present.loadNews()

        return view
    }
    class NewsPresenterFactory(val view: NewsContract.View, val recyclerModel: BaseRecyclerModel) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsPresenter(view, recyclerModel) as T
        }
    }

    override fun showProgressBar() {
        loadingProgressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        loadingProgressBar.visibility = View.INVISIBLE
    }
}