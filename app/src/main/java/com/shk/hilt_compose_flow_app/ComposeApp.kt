package com.shk.hilt_compose_flow_app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ComposeApp : Application(){
    companion object {
        lateinit var instance: ComposeApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}