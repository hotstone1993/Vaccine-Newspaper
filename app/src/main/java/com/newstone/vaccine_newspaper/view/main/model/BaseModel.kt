package com.newstone.vaccine_newspaper.view.main.model

interface BaseRecyclerModel {
    fun addItem(item: Any)

    fun getItemCount(): Int

    fun notifyData()
}