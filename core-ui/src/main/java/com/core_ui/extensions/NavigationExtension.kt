package com.core_ui.extensions

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.core_ui.R


fun NavController.safeNavigate(@IdRes id: Int, bundle: Bundle? = null, navOptions: NavOptions? = null) {
    val currentGraph = currentDestination?.parent ?: graph

    val useOptions = navOptions ?: PreMadeNavOptions.enterRightExitLeft()
    if (currentGraph.findNode(id) != null) {
        navigate(id, bundle, useOptions)
    }
}

object PreMadeNavOptions {

    fun enterRightExitLeft(
        alsoApply: NavOptions.Builder.() -> NavOptions.Builder = { this }
    ): NavOptions {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in_right)
            .setExitAnim(R.anim.slide_out_left)
            .setPopEnterAnim(R.anim.slide_in_left)
            .setPopExitAnim(R.anim.slide_out_right)
            .alsoApply()
            .build()
    }
}
