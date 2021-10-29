package com.newstone.vaccine_newspaper.view.main.util

import android.view.View

object BindingConverters {
    @JvmStatic
    fun isLoading(status: Boolean): Int {
        if(status) {
            return View.VISIBLE
        } else {
            return View.INVISIBLE
        }
    }
}