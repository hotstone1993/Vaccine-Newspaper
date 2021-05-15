package com.newstone.vaccine_newspaper.view.main.video.presenter

class VideoContract {
    interface View {
        fun showProgressBar()
        fun hideProgressBar()
    }

    interface ViewModel {
        fun loadVideo()
    }
}