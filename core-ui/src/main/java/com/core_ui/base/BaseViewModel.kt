package com.core_ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

interface Action
interface ViewState

abstract class BaseViewModel<VS: ViewState, A: Action> : ViewModel(), CoroutineScope {

    @Suppress("PropertyName", "VariableNaming")
    protected val _viewState = MutableLiveData<VS>()
    val viewState: LiveData<VS>
        get() = _viewState

    @Suppress("PropertyName", "VariableNaming")
    protected val _action = MutableLiveData<A>()
    val action: LiveData<A>
        get() = _action

    private val job = Job()
    override val coroutineContext = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}