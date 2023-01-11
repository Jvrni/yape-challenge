package com.bankuishtest.di

import com.service.BuildConfig
import com.service.helpers.BuildInfoHelper
import com.service.helpers.ServiceFactory
import com.service.helpers.Service
import com.service.search.repository.SearchRepository
import com.service.search.repository.SearchService
import com.service.search.usecase.GetRepositories
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val KOIN_RETROFIT = "retrofit"

val serviceModule = module {

    // ========= GENERAL SETTINGS

    single {
        Service
    }

    single {
        BuildInfoHelper()
    }

    single {
        BuildConfig()
    }

    single {
        GsonConverterFactory.create()
    }

    single(named(KOIN_RETROFIT)) {
        Retrofit
            .Builder()
            .baseUrl(get<BuildInfoHelper>().webApiUrl)
            .addConverterFactory(get<GsonConverterFactory>())
            .build()
    }

//     ========= Search

    single {
        SearchRepository(
            get<Service>(),
            get<SearchService>()
        )
    }

    single { GetRepositories(get<SearchRepository>()) }

    single {
        ServiceFactory.createService(
            get(named(KOIN_RETROFIT)),
            SearchService::class.java
        )
    }
}