package com.newstone.vaccine_newspaper.view.main.news

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.newstone.vaccine_newspaper.R

class NewsWebViewActivity : AppCompatActivity(){
    companion object {
        val WEBVIEW_URL_KEY = "NEWS_URL_KEY"
        val WEBVIEW_TITLE_KEY = "NEWS_TITLE_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_news_webview)
        val url: String = intent.getStringExtra(WEBVIEW_URL_KEY) ?: "ERROR"
        val title: String = intent.getStringExtra(WEBVIEW_TITLE_KEY) ?: "ERROR"
        val backBtn = findViewById<Button>(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }
        val webView = findViewById<WebView>(R.id.newsWebView)
        webView.setWebViewClient(WebViewClient())
        val settings = webView.getSettings()
        settings.setSupportMultipleWindows(false)
        settings.setJavaScriptCanOpenWindowsAutomatically(false)
        settings.setLoadWithOverviewMode(true)
        settings.setUseWideViewPort(true)
        settings.setSupportZoom(false)
        settings.setBuiltInZoomControls(false)
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE)
        settings.setDomStorageEnabled(true)
        settings.setJavaScriptEnabled(true)
        settings.setBlockNetworkImage(false)
        webView.loadUrl(url)
        val titleTextView = findViewById<TextView>(R.id.newsTitleTextView)
        titleTextView.text = title

        super.onCreate(savedInstanceState)
    }
}