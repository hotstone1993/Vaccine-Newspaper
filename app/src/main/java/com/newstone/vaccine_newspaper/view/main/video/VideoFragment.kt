package com.newstone.vaccine_newspaper.view.main.video

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsContract
import com.newstone.vaccine_newspaper.view.main.news.presenter.NewsPresenter
import com.newstone.vaccine_newspaper.view.main.video.adapter.VideoAdapter
import com.newstone.vaccine_newspaper.view.main.video.presenter.VideoContract
import com.newstone.vaccine_newspaper.view.main.video.presenter.VideoPresenter

class VideoFragment: Fragment(), VideoContract.View {
    private lateinit var progressBar: ProgressBar
    private val videoRecyclerAdapter by lazy {
        VideoAdapter(requireContext())
    }
    private val model: VideoPresenter by viewModels {
        VideoPresenterFactory(requireContext(), this, videoRecyclerAdapter)
    }
    private lateinit var videoRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        progressBar = view.findViewById(R.id.videoProgressBar)
        progressBar.visibility = View.INVISIBLE
        videoRecyclerView = view.findViewById(R.id.videoRecyclerView)
        videoRecyclerView.run {
            adapter = videoRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 1)
        }
        return view
    }

    class VideoPresenterFactory(val context: Context, val view: VideoContract.View, val recyclerModel: BaseRecyclerModel) :
            ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return VideoPresenter(context, view, recyclerModel) as T
        }
    }

    override fun showProgressBar() {
    }

    override fun hideProgressBar() {
    }
}