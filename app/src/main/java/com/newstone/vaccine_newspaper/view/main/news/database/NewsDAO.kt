package com.newstone.vaccine_newspaper.view.main.news.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

import java.util.*

@Dao
interface NewsDAO {
    @Insert(onConflict = REPLACE)
    fun insert(news: NewsEntity)

    @Query("SELECT * FROM news WHERE :currDate == date")
    fun getAllNews(currDate: String) : List<NewsEntity>

    @Delete
    fun delete(news: NewsEntity)
}