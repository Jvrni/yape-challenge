package com.bankuishtest.di

import com.ui.details.DetailsViewModel
import com.ui.search.SearchViewModel
import com.ui.splash.SplashViewModel
import org.koin.dsl.module

val uiModule = module {
    factory { SplashViewModel() }
    factory { SearchViewModel(get()) }
    factory { DetailsViewModel() }
}