package com.newstone.vaccine_newspaper.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.setting.SettingFragment
import com.newstone.vaccine_newspaper.view.main.util.replace
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment

class MainActivity : AppCompatActivity() {
    private val settingFragment : SettingFragment by lazy {
        SettingFragment()
    }
    private val newsFragment : NewsFragment by lazy {
        NewsFragment()
    }
    private val videoFragment : VideoFragment by lazy {
        VideoFragment()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replace(R.id.container, settingFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_camera -> {
                replace(R.id.container, newsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_more -> {
                replace(R.id.container, videoFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replace(R.id.container, settingFragment)

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}