package com.ui.home

import YapeTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.extensions.safeNavigate
import com.ui.details.DetailsFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalComposeUiApi
class HomeFragment : BaseFragment<HomeViewModel>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        super.onCreateView(inflater, container, savedInstanceState)

        setContent {
            YapeTheme {
                HomeScreen(viewModel) {
                    findNavController().safeNavigate(
                        com.ui.details.R.id.details_graph,
                        DetailsFragmentArgs(it).toBundle()
                    )
                }
            }
        }
    }
}