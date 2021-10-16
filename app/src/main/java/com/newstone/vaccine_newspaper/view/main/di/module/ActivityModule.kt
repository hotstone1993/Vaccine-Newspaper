package com.newstone.vaccine_newspaper.view.main.di.module

import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.room.PrimaryKey
import com.newstone.vaccine_newspaper.view.main.di.scpoe.ActivityContext
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity {
        return activity
    }

    @Provides
    @PerActivity
    fun provideNewsFragment(): NewsFragment = NewsFragment()

    @Provides
    @PerActivity
    fun provideVideoFragment(): VideoFragment = VideoFragment()
}