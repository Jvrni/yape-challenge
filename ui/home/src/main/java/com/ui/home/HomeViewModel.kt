package com.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.core.base.Action
import com.core.base.BaseViewModel
import com.core.base.ViewState
import com.core.components.ItemFilterViewEntity
import com.core.components.SingleSelectionRowFilterState
import com.core.components.SingleSelectionRowFilterViewEntity
import com.service.search.repository.Recipe
import com.service.search.usecase.GetRecipe
import kotlinx.coroutines.launch

enum class FilterOption {
    Time,
    Rating
}

sealed class RecipesState : ViewState {
    data class Loaded(
        val data: List<Recipe>,
        val isRefreshing: Boolean
    ) : RecipesState()

    object Empty : RecipesState()
}

class HomeViewModel(private val getRecipe: GetRecipe) :
    BaseViewModel<RecipesState, Action>() {

    private val _filterEntity = MutableLiveData<SingleSelectionRowFilterViewEntity>()
    val filterEntity: LiveData<SingleSelectionRowFilterViewEntity>
        get() = _filterEntity

    private val filterList = SingleSelectionRowFilterViewEntity(
        items = listOf(
            ItemFilterViewEntity(
                id = "0",
                title = FilterOption.Time.name,
                isVisible = true,
                state = SingleSelectionRowFilterState.Idle
            ),
            ItemFilterViewEntity(
                id = "1",
                title = FilterOption.Rating.name,
                isVisible = true,
                state = SingleSelectionRowFilterState.Idle
            )
        )
    )

    init {
        _filterEntity.value = filterList
        recipes()
    }

    fun recipes(filter: FilterOption? = null, text: String? = null) {
        viewModelScope.launch {
            _viewState.value = RecipesState.Loaded(emptyList(), true)
            val list = getRecipe.execute().run {
                when {
                    text != null && filter != null -> this.filter { recipe ->
                        recipe.name.lowercase()
                            .contains(text.lowercase()) || !recipe.ingredients.none {
                            it.name.lowercase().contains(text.lowercase())
                        }
                    }.sortedByDescending { recipe ->
                        when (filter) {
                            FilterOption.Rating -> recipe.rating
                            else -> recipe.timeMin
                        }
                    }

                    text != null -> this.filter { recipe ->
                        recipe.name.lowercase()
                            .contains(text.lowercase()) || !recipe.ingredients.none {
                            it.name.lowercase().contains(text.lowercase())
                        }
                    }

                    filter != null -> this.sortedByDescending { recipe ->
                        when (filter) {
                            FilterOption.Rating -> recipe.rating
                            else -> recipe.timeMin
                        }
                    }

                    else -> this
                }
            }

            if (list.isEmpty()) {
                _viewState.value = RecipesState.Empty
                return@launch
            }

            _viewState.value = RecipesState.Loaded(list, false)
        }
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
}

