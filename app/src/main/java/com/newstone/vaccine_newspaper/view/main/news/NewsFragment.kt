package com.newstone.vaccine_newspaper.view.main.news

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.di.VNApplication
import com.newstone.vaccine_newspaper.view.main.di.component.DaggerFragmentComponent
import com.newstone.vaccine_newspaper.view.main.di.module.FragmentModule
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.adapter.NewsAdapter
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsContract
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsPresenter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class NewsFragment: Fragment(), NewsContract.View, SwipeRefreshLayout.OnRefreshListener {
    private val present: NewsPresenter by viewModels {
        NewsPresenterFactory(requireActivity(), this@NewsFragment, newsRecyclerAdapter)
    }
    private val newsRecyclerAdapter: NewsAdapter by lazy {
        NewsAdapter(requireContext(), startWebViewActivityFunction)
    }
    private val startWebViewActivityFunction = { url: String, title: String -> Unit
        NewsBottomSheet.create(url, title)
            .show(requireActivity().supportFragmentManager, "NewsBottomSheet")
    }

    // UI
    lateinit var loadingProgressBar: ProgressBar
    private lateinit var newsRecyclerView: RecyclerView

    private lateinit var reloadBtn:Button
    private lateinit var dateTextView: TextView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @Inject
    lateinit var calendar: Calendar

    var datePicker: DatePickerDialog.OnDateSetListener =
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

        val fragmentComponent = DaggerFragmentComponent.builder().appComponent((requireActivity().application as VNApplication).appComponent).fragmentModule(
            FragmentModule(this)
        ).build()
        fragmentComponent.inject(this)

        // UI
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        swipeRefreshLayout = view.findViewById(R.id.pullToRefresh)
        swipeRefreshLayout.setOnRefreshListener(this)
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
        val appToolbar = view.findViewById<Toolbar>(R.id.appToolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(appToolbar)
        // Crawling
        present.loadNews()

        return view
    }
    class NewsPresenterFactory(val context: Context, val view: NewsContract.View, val recyclerModel: BaseRecyclerModel) :
        ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return NewsPresenter(context, view, recyclerModel) as T
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

    override fun onRefresh() {
        showProgressBar()
        resetNews()
        present.loadNews()
        swipeRefreshLayout.setRefreshing(false)
        hideProgressBar()
    }
}