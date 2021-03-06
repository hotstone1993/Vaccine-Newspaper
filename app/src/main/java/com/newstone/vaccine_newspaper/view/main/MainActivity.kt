package com.newstone.vaccine_newspaper.view.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.newstone.vaccine_newspaper.R
import com.newstone.vaccine_newspaper.databinding.ActivityMainBinding
import com.newstone.vaccine_newspaper.view.main.di.VNApplication
import com.newstone.vaccine_newspaper.view.main.di.component.DaggerActivityComponent
import com.newstone.vaccine_newspaper.view.main.di.module.ActivityModule
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.util.replace
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    val PERMISSION_LIST = listOf<String>(android.Manifest.permission.INTERNET, android.Manifest.permission.ACCESS_NETWORK_STATE)

    @Inject
    lateinit var newsFragment: NewsFragment
    @Inject
    lateinit var videoFragment: VideoFragment

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
            } else {
            }
        }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_news -> {
                replace(R.id.container, newsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_video -> {
                replace(R.id.container, videoFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val activityComponent = DaggerActivityComponent.builder().appComponent((application as VNApplication).appComponent).activityModule(
            ActivityModule(this)
        ).build()
        activityComponent.inject(this)

        requestPermission()

        replace(R.id.container, newsFragment)

        binding.navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
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
                Toast.makeText(this, "Permission must be granted.", Toast.LENGTH_LONG).show()
            } else {
                for(permission in PERMISSION_LIST) {
                    requestPermissionLauncher.launch(permission)
                }
            }
        }
    }
}