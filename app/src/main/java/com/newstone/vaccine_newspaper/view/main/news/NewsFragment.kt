package com.newstone.vaccine_newspaper.view.main.news

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsAdapter
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsContract
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsPresenter
import java.text.SimpleDateFormat
import java.util.*

class NewsFragment: Fragment(), NewsContract.View {
    private val present: NewsPresenter by viewModels {
        NewsPresenterFactory(this@NewsFragment, newsRecyclerAdapter)
    }
    private val newsRecyclerAdapter: NewsAdapter by lazy {
        NewsAdapter(requireContext(), startWebViewActivityFunction)
    }
    lateinit var loadingProgressBar: ProgressBar
    private lateinit var newsRecyclerView: RecyclerView
    private val startWebViewActivityFunction = { url: String, title: String -> Unit
        NewsBottomSheet.create(url, title)
            .show(requireActivity().supportFragmentManager, "NewsBottomSheet")
    }
    private lateinit var reloadBtn:Button
    private lateinit var dateTextView: TextView

    private val calendar =  Calendar.getInstance()
    var datePicker =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dateTextView.setText(SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(calendar.getTime()))
            resetNews()
            NewsRepository.setDate(SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(calendar.getTime()))
            present.loadNews()
        }


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
        reloadBtn = view.findViewById(R.id.reloadBtn)
        reloadBtn.setOnClickListener {
            resetNews()
            present.loadNews()
        }
        dateTextView = view.findViewById(R.id.dateTextView)
        dateTextView.text = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Calendar.getInstance().time)
        dateTextView.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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

    fun resetNews() {
        NewsRepository.resetData()
        newsRecyclerAdapter.clearData()
        newsRecyclerAdapter.notifyData()
    }
}