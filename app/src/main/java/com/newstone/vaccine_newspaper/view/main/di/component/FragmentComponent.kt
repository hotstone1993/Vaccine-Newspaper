package com.newstone.vaccine_newspaper.view.main.di.component

import androidx.fragment.app.Fragment
import com.newstone.vaccine_newspaper.view.main.di.module.FragmentModule
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [FragmentModule::class])
interface FragmentComponent {
    fun inject(fragment: Fragment)
}