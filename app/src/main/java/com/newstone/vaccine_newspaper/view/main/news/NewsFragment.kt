package com.newstone.vaccine_newspaper.view.main.news

import android.app.Application
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.databinding.FragmentNewsBinding
import com.newstone.vaccine_newspaper.view.main.di.VNApplication
import com.newstone.vaccine_newspaper.view.main.di.component.DaggerFragmentComponent
import com.newstone.vaccine_newspaper.view.main.di.module.FragmentModule
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsAdapter
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.viewmodel.NewsViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsFragment: Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: NewsViewModel by viewModels {
        NewsViewModelFactory(requireActivity().application, newsRecyclerAdapter)
    }
    private val newsRecyclerAdapter: NewsAdapter by lazy {
        NewsAdapter(requireContext(), startWebViewActivityFunction)
    }
    private val startWebViewActivityFunction = { url: String, title: String -> Unit
        NewsBottomSheet.create(url, title)
            .show(requireActivity().supportFragmentManager, "NewsBottomSheet")
    }

    // UI
    lateinit var biding: FragmentNewsBinding
    @Inject
    lateinit var calendar: Calendar

    var datePicker: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            biding.dateTextView.setText(SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(calendar.getTime()))
            NewsRepository.setDate(SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(calendar.getTime()))
            viewModel.loadNews(true)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        biding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        biding.vm = viewModel

        val fragmentComponent = DaggerFragmentComponent.builder().appComponent((requireActivity().application as VNApplication).appComponent).fragmentModule(
            FragmentModule(this)
        ).build()
        fragmentComponent.inject(this)

        // UI
        viewModel.loadingStatus.observe(requireActivity()) { isVisible ->
            if (isVisible) {
                biding.loadingProgressBar.visibility = View.VISIBLE
            } else {
                biding.loadingProgressBar.visibility = View.INVISIBLE
            }
        }
        biding.pullToRefresh.setOnRefreshListener(this)

        biding.newsRecyclerView.run {
            adapter = newsRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }

        biding.dateTextView.text = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(Calendar.getInstance().time)
        biding.dateTextView.setOnClickListener {
            DatePickerDialog(requireContext(), datePicker, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        (requireActivity() as AppCompatActivity).setSupportActionBar(biding.appToolbar)
        // Crawling
        viewModel.loadNews(true)

        return biding.root
    }
    class NewsViewModelFactory(private val application: Application, private val recyclerModel: BaseRecyclerModel) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsViewModel(application, recyclerModel) as T
        }
    }

    override fun onRefresh() {
        viewModel.showProgressBar()
        viewModel.loadNews(true)
        biding.pullToRefresh.setRefreshing(false)
        viewModel.hideProgressBar()
    }
}