package com.ui.splash

import YapeTheme
import Colors
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.compose.*
import com.core.base.BaseFragment
import com.core.extensions.safeNavigate
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<SplashViewModel>() {

     override val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        setContent {
            YapeTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Colors.background)
                ) {
                    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_animation))

                    LottieAnimation(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth(1f)
                            .fillMaxHeight(0.4f),
                        composition = composition,
                        iterations = LottieConstants.IterateForever,
                        clipSpec = LottieClipSpec.Progress(0f, 1f)
                    )
                }
            }

            LaunchedEffect(true) {
                delay(2000L)
                findNavController().popBackStack()
                findNavController().safeNavigate(com.ui.home.R.id.home_graph)
            }
        }
    }
}
