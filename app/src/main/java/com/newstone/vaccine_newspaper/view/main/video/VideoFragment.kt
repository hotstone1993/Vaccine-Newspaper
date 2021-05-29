package com.newstone.vaccine_newspaper.view.main.video

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.os.Bundle
import android.os.Handler
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.graphics.scale
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel
import com.newstone.vaccine_newspaper.view.main.video.PlaybackActivity.Companion.VIDEO_TITLE
import com.newstone.vaccine_newspaper.view.main.video.PlaybackActivity.Companion.VIDEO_URL
import com.newstone.vaccine_newspaper.view.main.video.adapter.VideoAdapter
import com.newstone.vaccine_newspaper.view.main.video.adapter.VideoItem
import com.newstone.vaccine_newspaper.view.main.video.presenter.VideoContract
import com.newstone.vaccine_newspaper.view.main.video.presenter.VideoPresenter
import java.net.URL
import kotlin.concurrent.thread
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;


class VideoFragment: Fragment(), VideoContract.View {
    private val VIDEO_RATE = 9.0f / 16.0f
    private lateinit var progressBar: ProgressBar
    private val videoRecyclerAdapter by lazy {
        VideoAdapter(requireContext(), startPlaybackActivityFunction)
    }
    private val model: VideoPresenter by viewModels {
        VideoPresenterFactory(requireContext(), this, videoRecyclerAdapter)
    }
    private lateinit var videoRecyclerView: RecyclerView
    private val startPlaybackActivityFunction = { url: String, title: String -> Unit
        val intent = Intent(requireContext(), PlaybackActivity::class.java)
        intent.putExtra(VIDEO_URL, url)
        intent.putExtra(VIDEO_TITLE, title)
        requireActivity().startActivity(intent)
    }

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

    override fun onStart() {
        super.onStart()
        val handler = Handler()

        if(videoRecyclerAdapter.itemCount == 0) {
            thread {
                for (i in 0 until 1) {
                    var previewBitmap: Bitmap?
                    var channelIcon: Bitmap?

                    try {
                        previewBitmap = BitmapFactory.decodeStream(URL("https://i.ytimg.com/an_webp/GsBqqFXvAYI/mqdefault_6s.webp?du=3000&sqp=CNTUgoUG&rs=AOn4CLDjrfaSYw2c1AhY8TnBd1aRxq_A4A").openConnection().getInputStream())
                        channelIcon = BitmapFactory.decodeStream(URL("https://yt3.ggpht.com/ytc/AAUvwniTc1jeP6U8pTz7E5JktLZiMcMIJ9AWYRY4LGueZg=s48-c-k-c0x00ffffff-no-rj").openConnection().getInputStream())
                    } catch (e: Exception) {
                        previewBitmap = null
                        channelIcon = null
                    }
                    val display = requireActivity().windowManager.defaultDisplay
                    val point = Point()
                    display.getSize(point)
                    val width = point.x
                    previewBitmap = previewBitmap?.scale(width, (width * VIDEO_RATE).toInt())
                    object : YouTubeExtractor(requireContext()) {
                        override fun onExtractionComplete(ytFiles: SparseArray<YtFile>?, vMeta: VideoMeta?) {
                            if (ytFiles != null) {
                                val itag = 22
                                val downloadUrl: String = ytFiles[itag].getUrl()
                                videoRecyclerAdapter.addItem(VideoItem(downloadUrl, previewBitmap, "애플 광고에서 들은 그 노래! 간만에 등장한 고막 취저 밴드ㅣAJR(에이제이알) 이야기",
                                        false, "2021. 3. 31", "우키팝", "176,928회", "9:05", channelIcon))
                                handler.post {
                                    videoRecyclerAdapter.notifyData()
                                }
                            }
                        }
                    }.extract("https://youtu.be/Uvtf4V5pOn8", true, true)
                }
            }
        }
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