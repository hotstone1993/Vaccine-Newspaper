package com.newstone.vaccine_newspaper.view.main.di.module

import androidx.fragment.app.Fragment
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import dagger.Module
import dagger.Provides

@Module
class FragmentModule(private val fragment: Fragment) {
    @Provides
    @PerActivity
    fun provideFragment(): Fragment = fragment


}