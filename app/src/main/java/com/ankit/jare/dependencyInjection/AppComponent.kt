package com.ankit.jare.dependencyInjection

import com.ankit.jare.WiproApp
import dagger.Component

@Component(
        modules = [
            ActivitiesModule::class
        ]
)

interface AppComponent {
    fun inject(wiproApp: WiproApp)
}