package com.newstone.vaccine_newspaper.view.main.di.module

import android.app.DatePickerDialog
import androidx.fragment.app.Fragment
import com.newstone.vaccine_newspaper.view.main.di.scpoe.PerActivity
import com.newstone.vaccine_newspaper.view.main.news.data.NewsRepository
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*

@Module
class FragmentModule(private val fragment: Fragment) {
    @Provides
    @PerActivity
    fun provideFragment(): Fragment = fragment

    @Provides
    @PerActivity
    fun provideCalendar(): Calendar = Calendar.getInstance()
}