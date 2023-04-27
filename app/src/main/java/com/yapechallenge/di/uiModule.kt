package com.yapechallenge.di

import com.ui.details.DetailsViewModel
import com.ui.home.HomeViewModel
import com.ui.map.MapViewModel
import com.ui.splash.SplashViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { SplashViewModel() }
    factory { HomeViewModel(get()) }
    factory { DetailsViewModel() }
    factory { MapViewModel() }
}