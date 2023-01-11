package com.core_ui.base

import androidx.fragment.app.Fragment

abstract class BaseFragment<ViewModel: BaseViewModel<*,*>>: Fragment() {

    abstract val viewModel: ViewModel
}