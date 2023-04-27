package com.ui.home

import Colors
import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.core.components.*
import com.core.theme.custom.Typography
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.service.search.repository.Recipe

@ExperimentalComposeUiApi
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onAction: (Recipe) -> Unit
) {
    val state = viewModel.viewState.observeAsState().value
    val filterEntity = viewModel.filterEntity.observeAsState().value

    val filterOption = rememberSaveable { mutableStateOf<FilterOption?>(null) }
    val text = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    val clearFilter: () -> Unit = {
        filterOption.value = null
        filterEntity?.let { viewModel.mapFilters(it, false) }
        viewModel.recipes(text = text.value, filter = filterOption.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        Text(
            modifier = Modifier
                .padding(top = Dimens.xlarge_padding)
                .padding(horizontal = Dimens.large_padding),
            text = stringResource(id = R.string.home_screen_title),
            color = Colors.text,
            style = Typography.Label.small
        )

        Text(
            modifier = Modifier.padding(horizontal = Dimens.large_padding),
            text = stringResource(id = R.string.home_screen_subtitle),
            color = Colors.unSelectText,
            style = Typography.Label.small
        )

        TextField(
            modifier = Modifier
                .padding(top = Dimens.medium_padding)
                .padding(horizontal = Dimens.large_padding)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        viewModel.recipes()
                    })
                },
            shape = RoundedCornerShape(Dimens.extra_biggest_corner_radius),
            height = 55.dp,
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.value.isNotEmpty()) text.value = ""
                        viewModel.recipes(text = text.value, filter = filterOption.value)
                    },
                    painter = painterResource(id = if (text.value.isNotEmpty()) com.core.R.drawable.ic_close else R.drawable.ic_search),
                    tint = Colors.unSelectText,
                    contentDescription = ""
                )
            },
            placeHolder = stringResource(id = R.string.home_screen_label_input),
            text = text,
            keyboardActions = KeyboardActions(onSearch = {
                viewModel.recipes(text = text.value, filter = filterOption.value)
                keyboardController?.hide()
            }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )

        Text(
            modifier = Modifier
                .padding(top = Dimens.xlarge_padding)
                .padding(bottom = Dimens.medium_padding)
                .padding(horizontal = Dimens.large_padding),
            text = stringResource(id = R.string.home_screen_title_filter),
            color = Colors.text,
            style = Typography.Label.small
        )

        filterEntity?.let {
            SingleSelectionRowFilter(
                filtersItems = it,
                onClearFilter = { clearFilter() }
            ) { item ->
                filterOption.value = FilterOption.valueOf(item.title)
                viewModel.mapFilters(filterEntity, false, item.id)
                viewModel.recipes(filter = FilterOption.valueOf(item.title), text = text.value)
            }
        }

        state?.let { state ->
            when (state) {
                is RecipesState.Loaded -> {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                        onRefresh = { viewModel.recipes(text = text.value, filter = filterOption.value) }
                    ) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Colors.background)
                                .padding(top = Dimens.large_padding),
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items(state.data) { recipe ->
                                FoodCard(
                                    imageUrl = recipe.image,
                                    name = recipe.name,
                                    timeMin = recipe.timeMin,
                                    rating = recipe.rating
                                ) {
                                    onAction.invoke(recipe)
                                }
                            }
                        }
                    }
                }
                else -> EmptyState(description = R.string.home_screen_empty_description)
            }
        }
    }
}