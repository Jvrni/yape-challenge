package com.yapechallenge.di

import com.service.BuildConfig
import com.service.helpers.BuildInfoHelper
import com.service.helpers.ServiceFactory
import com.service.helpers.Service
import com.service.search.repository.RecipeRepository
import com.service.search.repository.RecipeService
import com.service.search.usecase.GetRecipe
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
        RecipeRepository(
            get<Service>(),
            get<RecipeService>()
        )
    }

    single { GetRecipe(get<RecipeRepository>()) }

    single {
        ServiceFactory.createService(
            get(named(KOIN_RETROFIT)),
            RecipeService::class.java
        )
    }
}