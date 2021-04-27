package com.newstone.vaccine_newspaper.view.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsAdapter
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsData

class NewsFragment: Fragment() {
    private val newsRecyclerAdapter: NewsAdapter by lazy {
        NewsAdapter(requireContext())
    }
    private lateinit var newsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        newsRecyclerAdapter.addItem(NewsData("백신 뉴스1", "www.newstone.com", resources.getDrawable(R.drawable.ic_item_defalut, null)))
        newsRecyclerAdapter.addItem(NewsData("백신 뉴스2", "www.newstone.com", resources.getDrawable(R.drawable.ic_item_defalut, null)))
        newsRecyclerAdapter.addItem(NewsData("백신 뉴스3", "www.newstone.com", resources.getDrawable(R.drawable.ic_item_defalut, null)))
        newsRecyclerAdapter.addItem(NewsData("백신 뉴스4", "www.newstone.com", resources.getDrawable(R.drawable.ic_item_defalut, null)))


        newsRecyclerView = view.findViewById(R.id.newsRecyclerView)
        newsRecyclerView.run {
            adapter = newsRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }

        return view
    }
}