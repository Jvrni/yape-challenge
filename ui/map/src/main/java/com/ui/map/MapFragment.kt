package com.ui.map

import YapeTheme
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.core.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MapFragment : BaseFragment<MapViewModel>() {

    override val viewModel by viewModel<MapViewModel>()
    private val args by navArgs<MapFragmentArgs>()

    override fun onResume() {
        super.onResume()
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, false)

        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            YapeTheme {
                MapScreen(
                    args.data,
                    onBack = {
                        findNavController().popBackStack()
                    }
                ) {
                    val gmmIntentUri =
                        Uri.parse("google.streetview:cbll=${args.data.lat},${args.data.long}")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    startActivity(mapIntent)
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        WindowCompat.setDecorFitsSystemWindows(requireActivity().window, true)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}