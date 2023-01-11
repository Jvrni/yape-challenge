import org.gradle.api.artifacts.dsl.DependencyHandler


object AppDependencies {
    //std lib
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"

    //android ui
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    private const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    private const val materialGoogle = "com.google.android.material:material:${Versions.googleMaterial}"
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val composeMaterial = "androidx.compose.material:material:${Versions.compose}"
    private const val composeUi = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    private const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    private const val lottie = "com.airbnb.android:lottie-compose:${Versions.lottie}"
    private const val lifeCycle = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    private const val ComposeConstraint = "androidx.constraintlayout:constraintlayout-compose:${Versions.composeConstraint}"
    private const val systemUi = "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemUi}"
    private const val composeRuntimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
    private const val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"
    private const val paging = "androidx.paging:paging-compose:${Versions.paging}"
    private const val pagingRuntime = "androidx.paging:paging-runtime:${Versions.pagingRuntime}"
    private const val glide = "com.github.skydoves:landscapist-glide:${Versions.glide}"
    private const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:${Versions.swipeRefresh}"

    //coroutines
    private const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"

    //retrofit
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"

    //koin
    private const val koin = "io.insert-koin:koin-core:${Versions.koin}"
    private const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    private const val koinCompose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    private const val koinTest = "io.insert-koin:koin-core:${Versions.koin}"

    //test libs
    private const val junit = "junit:junit:${Versions.junit}"
    private const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val mockk = "io.mockk:mockk:${Versions.mockk}"
    private const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    private const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val composeUiJunit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
    private const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    private const val coreTest = "androidx.arch.core:core-testing:${Versions.coreTest}"

    val appLibraries = mutableListOf<String>().apply {
        add(kotlinStdLib)
        add(appCompat)
        add(fragment)
        add(lottie)
        add(materialGoogle)
        add(coreKtx)
        add(composeMaterial)
        add(composeUi)
        add(composeUiTooling)
        add(lifeCycle)
        add(ComposeConstraint)
        add(systemUi)
        add(composeRuntimeLivedata)
        add(navigationFragment)
        add(navigationUi)
        add(koin)
        add(koinAndroid)
        add(koinCompose)
        add(coroutines)
        add(viewModel)
        add(livedata)
        add(paging)
        add(pagingRuntime)
        add(glide)
        add(swipeRefresh)
    }

    val serviceLibraries = mutableListOf<String>().apply {
        add(retrofit)
        add(coroutinesCore)
        add(coroutines)
        add(gson)
        add(gsonConverter)
        add(paging)
    }

    val kaptLibraries = mutableListOf<String>().apply {
    }

    val androidTestLibraries = mutableListOf<String>().apply {
        add(extJUnit)
        add(espressoCore)
        add(composeUiJunit)
        add(koinTest)
        add(assertj)
        add(coreTest)
    }

    val testLibraries = mutableListOf<String>().apply {
        add(junit)
        add(mockk)
        add(coroutinesTest)
        add(coreTest)
    }
}

//util functions for adding the different type dependencies from build.gradle.kts file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}