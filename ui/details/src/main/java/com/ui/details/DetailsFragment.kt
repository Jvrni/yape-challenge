package com.ui.details

import YapeTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.core.base.BaseFragment
import com.core.extensions.safeNavigate
import com.ui.map.MapFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment<DetailsViewModel>() {

    override val viewModel by viewModel<DetailsViewModel>()
    private val args by navArgs<DetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {

        setContent {
            YapeTheme {
                DetailsScreen(args.data, onBack = { findNavController().popBackStack() }) {
                    findNavController().safeNavigate(
                        com.ui.map.R.id.map_graph,
                        MapFragmentArgs(args.data.location).toBundle()
                    )
                }
            }
        }
    }
}