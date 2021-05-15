package com.newstone.vaccine_newspaper.view.main.video.presenter

import android.content.Context
import androidx.lifecycle.ViewModel
import com.newstone.vaccine_newspaper.view.main.model.BaseRecyclerModel

class VideoPresenter(val context: Context, private val view: VideoContract.View, private val recyclerModel: BaseRecyclerModel): ViewModel(), VideoContract.ViewModel {
    override fun loadVideo() {
    }

}