package com.newstone.vaccine_newspaper.view.main.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = arrayOf(NewsEntity::class), version = 2)
abstract class NewsDatabase: RoomDatabase() {
    abstract fun newsDAO(): NewsDAO

    companion object {
        @Volatile private var instance: NewsDatabase? = null
        @JvmStatic fun getInstance(context: Context): NewsDatabase = instance ?: synchronized(NewsDatabase::class) {
            instance ?: Room.databaseBuilder(context.applicationContext, NewsDatabase::class.java, "News Database").fallbackToDestructiveMigration().build()
        }
    }
}