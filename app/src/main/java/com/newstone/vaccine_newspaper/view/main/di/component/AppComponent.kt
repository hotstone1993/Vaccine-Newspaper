package com.newstone.vaccine_newspaper.view.main.di.component

import android.app.Activity
import com.newstone.vaccine_newspaper.view.main.MainActivity
import com.newstone.vaccine_newspaper.view.main.di.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent{
}