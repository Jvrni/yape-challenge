package com.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.core_ui.base.Action
import com.core_ui.base.BaseViewModel
import com.core_ui.base.ViewState
import com.core_ui.components.ItemFilterViewEntity
import com.core_ui.components.SingleSelectionRowFilterState
import com.core_ui.components.SingleSelectionRowFilterViewEntity
import com.service.search.repository.Repository
import com.service.search.usecase.GetRepositories
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed class SearchState : ViewState {
    data class Loaded(val data: Flow<PagingData<Repository>>) : SearchState()
    object Empty : SearchState()
}

sealed class SearchAction : Action {

}

class SearchViewModel(private val getRepositories: GetRepositories) :
    BaseViewModel<SearchState, SearchAction>() {

    private val _filterEntity = MutableLiveData<SingleSelectionRowFilterViewEntity>()
    val filterEntity: LiveData<SingleSelectionRowFilterViewEntity>
        get() = _filterEntity

    private val filterList = SingleSelectionRowFilterViewEntity(
        items = listOf(
            ItemFilterViewEntity(
                id = "0",
                title = "Kotlin",
                isVisible = true,
                state = SingleSelectionRowFilterState.Selected
            ),
            ItemFilterViewEntity(
                id = "1",
                title = "Java",
                isVisible = false,
                state = SingleSelectionRowFilterState.Idle
            ),
            ItemFilterViewEntity(
                id = "2",
                title = "Swift",
                isVisible = false,
                state = SingleSelectionRowFilterState.Idle
            ),
            ItemFilterViewEntity(
                id = "3",
                title = "Java Script",
                isVisible = false,
                state = SingleSelectionRowFilterState.Idle
            ),
        )
    )

    init {
        _filterEntity.value = filterList
        search(LANGUAGE_DEFAULT)
    }

    fun search(language: String) {
        viewModelScope.launch {
            _viewState.value =
                SearchState.Loaded(getRepositories.execute(language).cachedIn(viewModelScope))
        }
    }

    fun cleanSearch() {
        _viewState.value = SearchState.Empty
    }

    fun mapFilters(
        viewEntity: SingleSelectionRowFilterViewEntity,
        isFirstSelected: Boolean,
        filter: String? = null
    ) {
        _filterEntity.value = SingleSelectionRowFilterViewEntity(viewEntity.items.map {
            ItemFilterViewEntity(
                id = it.id,
                title = it.title,
                isVisible = when {
                    filter == null -> true
                    isFirstSelected || filter == it.id -> true
                    else -> false
                },
                state = when {
                    filter == null -> SingleSelectionRowFilterState.Idle
                    isFirstSelected || filter == it.id -> SingleSelectionRowFilterState.Selected
                    else -> SingleSelectionRowFilterState.Idle
                }
            )
        })
    }

    companion object {
        private const val LANGUAGE_DEFAULT = "kotlin"
    }
}

