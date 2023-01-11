package com.service.helpers

import com.service.BuildConfig

class BuildInfoHelper {

    val webApiUrl: String
        get() = BuildConfig.WEB_API_URL
}