package com.newstone.vaccine_newspaper.view.main.di.component

import com.newstone.vaccine_newspaper.view.main.di.module.PresenterModule
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import dagger.Component

@PerActivity
@Component(dependencies = [AppComponent::class], modules = [PresenterModule::class])
interface PresenterComponent {
}