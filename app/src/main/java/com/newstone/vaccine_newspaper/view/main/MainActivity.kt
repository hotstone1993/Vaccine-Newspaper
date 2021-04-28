package com.newstone.vaccine_newspaper.view.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.setting.SettingFragment
import com.newstone.vaccine_newspaper.view.main.util.replace
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment

class MainActivity : AppCompatActivity() {
    val PERMISSION_LIST = listOf<String>(android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE)
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()

        replace(R.id.container, settingFragment)

        val navigation: BottomNavigationView = findViewById(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun checkPermission(): Boolean {
        for(permission in PERMISSION_LIST) {
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestPermission() {
        if(!checkPermission()) {
            if(shouldShowRequestPermissionRationale(PERMISSION_LIST[0])) {
                Toast.makeText(this, "Permission must be granted.", Toast.LENGTH_LONG)
            } else {
                for(permission in PERMISSION_LIST) {
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }
}