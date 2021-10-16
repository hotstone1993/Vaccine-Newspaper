package com.newstone.vaccine_newspaper.view.main.di.component

import androidx.fragment.app.Fragment
import com.newstone.vaccine_newspaper.view.main.di.module.FragmentModule
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import com.newstone.vaccine_newspaper.view.main.news.NewsFragment
import com.newstone.vaccine_newspaper.view.main.video.VideoFragment
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: NewsFragment)
    fun inject(fragment: VideoFragment)
}