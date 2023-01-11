package com.ui.search

import Colors
import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.core_ui.components.*
import com.core_ui.theme.custom.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.service.search.repository.Repository

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(
    state: SearchState?,
    filterEntity: SingleSelectionRowFilterViewEntity,
    viewModel: SearchViewModel,
    onAction: (Repository) -> Unit
) {

    val clearFilter: () -> Unit = {
        viewModel.mapFilters(filterEntity, false)
        viewModel.cleanSearch()
    }

    val text = rememberSaveable { mutableStateOf("") }
    val isRefreshing = rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val language = text.value.ifEmpty { filterEntity.items.firstOrNull { it.state == SingleSelectionRowFilterState.Selected }?.title } ?: ""

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        TextField(
            modifier = Modifier
                .padding(top = Dimens.large_padding)
                .padding(horizontal = Dimens.large_padding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        viewModel.search(text.value)
                    })
                },
            shape = RoundedCornerShape(Dimens.extra_biggest_corner_radius),
            height = 55.dp,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable { if (text.value.isNotEmpty()) text.value = "" },
                    painter = painterResource(id = if (text.value.isNotEmpty()) com.core_ui.R.drawable.ic_close else R.drawable.ic_search),
                    tint = Colors.tertiary,
                    contentDescription = ""
                )
            },
            placeHolder = stringResource(id = R.string.search_screen_label_input),
            text = text,
            keyboardActions = KeyboardActions(onSearch = {
                clearFilter()
                viewModel.search(text.value)
                keyboardController?.hide()
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )

        Text(
            modifier = Modifier
                .padding(top = Dimens.xlarge_padding)
                .padding(bottom = Dimens.medium_padding)
                .padding(horizontal = Dimens.large_padding),
            text = stringResource(id = R.string.search_screen_title),
            color = Colors.text,
            style = Typography.Label.small
        )

        SingleSelectionRowFilter(
            filtersItems = filterEntity,
            onClearFilter = { clearFilter() }
        ) { item ->
            viewModel.mapFilters(filterEntity, false, item.id)
            viewModel.search(item.title)
        }

        state?.let { state ->
            when (state) {
                is SearchState.Loaded -> {
                    val list = state.data.collectAsLazyPagingItems()

                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = isRefreshing.value),
                        onRefresh = {
                            isRefreshing.value = true
                            viewModel.search(language)
                        }
                    ) {
                        LazyColumn(modifier = Modifier.padding(top = Dimens.large_padding)) {
                            items(items = list) { repository ->
                                repository?.let { item ->
                                    isRefreshing.value = false

                                    ItemRepository(
                                        imageUrl = item.owner.image,
                                        name = item.name,
                                        description = item.description ?: ""
                                    ) {
                                        onAction.invoke(item)
                                    }
                                }
                            }

                            when {
                                list.loadState.refresh is LoadState.Loading -> item {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Colors.primary
                                        )
                                    }
                                }

                                list.loadState.append is LoadState.NotLoading -> item {
                                    isRefreshing.value = false
                                    EmptyState { viewModel.search(language) }
                                }
                                list.loadState.append is LoadState.Loading -> item {
                                    Box(modifier = Modifier.fillMaxSize()) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Colors.primary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                else -> EmptyState(description = R.string.search_screen_empty_description)
            }
        }
    }
}