package com.yapechallenge

import android.app.Application
import com.yapechallenge.di.serviceModule
import com.yapechallenge.di.uiModule
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                listOf(
                    serviceModule,
                    uiModule
                )
            )
        }
    }
}