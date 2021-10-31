package com.newstone.vaccine_newspaper.view.main.util

interface ViewEvent {
    fun excuteEvent(event: String, vararg args: Any)
}