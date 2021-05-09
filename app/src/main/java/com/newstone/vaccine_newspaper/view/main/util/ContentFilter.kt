package com.newstone.vaccine_newspaper.view.main.util

class ContentFilter private constructor() {
    val FILTER_LIST = listOf("백신", "COVID", "코로나", "접종")

    companion object {
        @Volatile private var instance : ContentFilter? = null
        @JvmStatic fun getInstance(): ContentFilter =
                instance ?: synchronized(this) {
                    instance ?: ContentFilter().also {
                        instance = it
                    }
                }
    }

    fun check(title: String): Boolean {
        for(f in FILTER_LIST) {
            if(title.contains(f))
                return true
        }
        return false
    }

}