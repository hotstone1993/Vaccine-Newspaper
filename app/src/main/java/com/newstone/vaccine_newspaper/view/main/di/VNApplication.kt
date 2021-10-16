package com.newstone.vaccine_newspaper.view.main.di

import android.app.Application
import com.newstone.vaccine_newspaper.view.main.di.component.AppComponent
import com.newstone.vaccine_newspaper.view.main.di.component.DaggerAppComponent
import com.newstone.vaccine_newspaper.view.main.di.module.AppModule

class VNApplication: Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}