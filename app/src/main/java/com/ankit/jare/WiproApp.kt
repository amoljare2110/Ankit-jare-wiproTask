package com.ankit.jare

import android.app.Activity
import android.app.Application
import com.ankit.jare.dependencyInjection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class WiproApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        instance = this

        var appComponent = DaggerAppComponent.create()
        appComponent.Inject(this)
    }

    companion object {
        lateinit var instance: WiproApp
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }
}