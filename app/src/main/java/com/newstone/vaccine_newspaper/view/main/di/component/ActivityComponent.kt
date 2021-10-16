package com.newstone.vaccine_newspaper.view.main.di.component

import com.newstone.vaccine_newspaper.view.main.MainActivity
import com.newstone.vaccine_newspaper.view.main.di.module.ActivityModule
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.video.PlaybackActivity
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    // Activity
    fun inject(activity: MainActivity)
    fun inject(activity: PlaybackActivity)
}