package com.newstone.vaccine_newspaper.view.main.data

interface BaseDataSource {
    fun loadData(dataList: (List<Any>) -> Unit)
}