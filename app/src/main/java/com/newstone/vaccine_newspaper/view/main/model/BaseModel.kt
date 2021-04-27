package com.newstone.vaccine_newspaper.view.main.model

interface BaseModel {
    fun addItem(item: Any)

    fun getItemCount(): Int

    fun notifyDataSetChang()
}