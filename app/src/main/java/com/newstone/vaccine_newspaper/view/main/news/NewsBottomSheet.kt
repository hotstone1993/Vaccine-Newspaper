package com.newstone.vaccine_newspaper.view.main.news

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.newstone.vaccine_newspaper.R

class NewsBottomSheet() : BottomSheetDialogFragment() {
    companion object {
        val WEBVIEW_URL_KEY = "NEWS_URL_KEY"
        val WEBVIEW_TITLE_KEY = "NEWS_TITLE_KEY"

        fun create(newsUrl: String, newsTitle: String): NewsBottomSheet = NewsBottomSheet().apply {
            arguments = Bundle().apply {
                putString(WEBVIEW_URL_KEY, newsUrl)
                putString(WEBVIEW_TITLE_KEY, newsTitle)
            }
        }
    }
    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    if (!isRemoving) dismiss()
                }
                else -> {
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottomsheet_news_webview, container, false)
        val backBtn = view.findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {
            dismiss()
        }
        val url = arguments?.getString(WEBVIEW_URL_KEY) ?: ""
        val title = arguments?.getString(WEBVIEW_TITLE_KEY) ?: ""
        val bottomSheet: RelativeLayout = view.findViewById(R.id.rootBottomSheet)
        val webView = view.findViewById<WebView>(R.id.newsWebView)
        webView.setWebViewClient(WebViewClient())
        val settings = webView.getSettings()
        settings.setSupportMultipleWindows(false)
        settings.setJavaScriptCanOpenWindowsAutomatically(false)
        settings.setLoadWithOverviewMode(true)
        settings.setUseWideViewPort(true)
        settings.setSupportZoom(false)
        settings.setBuiltInZoomControls(false)
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        settings.setDomStorageEnabled(true)
        settings.setJavaScriptEnabled(true)
        settings.setBlockNetworkImage(false)
        webView.loadUrl(url)
        webView.setOnTouchListener { v, event ->
            bottomSheet.requestDisallowInterceptTouchEvent(true)
            return@setOnTouchListener false
        }
        val titleTextView = view.findViewById<TextView>(R.id.newsTitleTextView)
        titleTextView.text = title

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheet =
                dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            BottomSheetBehavior.from(bottomSheet).apply {
                state = BottomSheetBehavior.STATE_EXPANDED
                addBottomSheetCallback(bottomSheetCallback)
            }
        }
        return dialog
    }
}