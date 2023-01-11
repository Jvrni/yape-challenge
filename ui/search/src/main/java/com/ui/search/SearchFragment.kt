package com.ui.search

import BankuishTestTheme
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.findNavController
import com.core_ui.base.BaseFragment
import com.core_ui.extensions.safeNavigate
import com.ui.details.DetailsFragmentArgs
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalComposeUiApi
class SearchFragment : BaseFragment<SearchViewModel>() {

    override val viewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        super.onCreateView(inflater, container, savedInstanceState)

        setContent {
            val state = viewModel.viewState.observeAsState().value
            val filterEntity = viewModel.filterEntity.observeAsState().value

            BankuishTestTheme {
                filterEntity?.let {
                    SearchScreen(state, it, viewModel) {
                        findNavController().safeNavigate(
                            com.ui.details.R.id.details_graph,
                            DetailsFragmentArgs(it).toBundle()
                        )
                    }
                }
            }
        }
    }
}