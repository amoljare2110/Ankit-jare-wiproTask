package com.ankit.jare

import android.app.Application

class WiproApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: WiproApp
    }
}