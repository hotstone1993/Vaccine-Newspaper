package com.newstone.vaccine_newspaper.view.main.news.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey
    val url: String,
    @ColumnInfo(name = "date")
    val date: String) {
}