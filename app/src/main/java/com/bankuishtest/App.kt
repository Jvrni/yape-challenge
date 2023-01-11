package com.bankuishtest

import android.app.Application
import com.bankuishtest.di.serviceModule
import com.bankuishtest.di.uiModule
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