package com.core_ui.components

import Dimens
import Colors
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.core_ui.theme.custom.Typography
import com.core_ui.R


@Composable
fun SingleSelectionRowFilter(
    modifier: Modifier = Modifier,
    onClearFilter: (String) -> Unit,
    filtersItems: SingleSelectionRowFilterViewEntity,
    onSelectFilter: (ItemFilterViewEntity) -> Unit
    ) {
    val isSelected = filtersItems.items.firstOrNull { it.isVisible }?.state == SingleSelectionRowFilterState.Selected

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(Dimens.large_padding, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(if (isSelected) 0.dp else Dimens.small_padding)
    ) {
        itemsIndexed(items = filtersItems.items) { index, item ->

            if (index == 0) {
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn(
                        animationSpec = TweenSpec(800)
                    ) + slideInHorizontally(
                        animationSpec = TweenSpec(400, delay = 400),
                        initialOffsetX = { -it }
                    ) + expandHorizontally(
                        animationSpec = TweenSpec(400),
                        expandFrom = Alignment.CenterHorizontally
                    ),
                    exit = fadeOut(
                        animationSpec = TweenSpec(800)
                    ) + slideOutHorizontally(
                        animationSpec = TweenSpec(500)
                    ) + shrinkHorizontally(
                        animationSpec = TweenSpec(800)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ClearFilterButton(
                            modifier = Modifier.size(38.dp),
                            action = { onClearFilter.invoke(item.id) }
                        )
                        Spacer(modifier = Modifier.width(Dimens.small_margin))
                    }
                }
            }

            AnimatedVisibility(
                modifier = Modifier.clip(CircleShape),
                visible = item.isVisible,
                enter = fadeIn(
                    animationSpec = TweenSpec(600, delay = 400)
                ) + expandHorizontally(
                    animationSpec = TweenSpec(500),
                    expandFrom = Alignment.Start
                ),
                exit = fadeOut(
                    animationSpec = TweenSpec(200)
                ) + shrinkHorizontally(
                    animationSpec = TweenSpec(800),
                    shrinkTowards = Alignment.Start
                )
            ) {
                Row(
                    modifier = Modifier.clip(CircleShape),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RowFilterOutlinedButtonByState(item = item, onSelectFilter = onSelectFilter)
                }
            }
        }
    }
}

@Composable
fun RowFilterOutlinedButton(
    modifier: Modifier = Modifier,
    borderColor: Color,
    colors: ButtonColors,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        modifier = modifier.height(38.dp),
        border = BorderStroke(Dimens.medium_stroke, borderColor),
        colors = colors,
        shape = CircleShape,
        onClick = onClick,
        content = content
    )
}

@Composable
private fun RowFilterOutlinedButtonByState(
    modifier: Modifier = Modifier,
    item: ItemFilterViewEntity,
    onSelectFilter: (ItemFilterViewEntity) -> Unit
) {
    when (item.state) {
        SingleSelectionRowFilterState.Idle -> {
            RowFilterOutlinedButton(
                modifier = modifier,
                borderColor = Colors.border,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Colors.background,
                    contentColor = Colors.primary
                ),
                onClick = { onSelectFilter.invoke(item) }
            ) {
                Text(
                    text = item.title,
                    style = Typography.Label.small,
                    color = Colors.text,
                )
            }
        }
        SingleSelectionRowFilterState.Selected -> {
            RowFilterOutlinedButton(
                modifier = modifier,
                borderColor = Colors.primary,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.White,
                    contentColor = Colors.primary
                ),
                onClick = { onSelectFilter.invoke(item) }
            ) {
                Text(
                    text = item.title,
                    style = Typography.Label.small,
                    color = Colors.primary,
                )
            }
        }
        SingleSelectionRowFilterState.Disable -> {
            RowFilterOutlinedButton(
                modifier = modifier,
                borderColor = Colors.border,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Colors.buttonDisable,
                    contentColor = Colors.buttonDisable
                ),
                onClick = { }
            ) {
                Text(
                    text = item.title,
                    style = Typography.Label.small,
                    color = Colors.text
                )
            }
        }
    }
}

@Composable
private fun ClearFilterButton(
    modifier: Modifier,
    action: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { action.invoke() },
        shape = CircleShape,
        backgroundColor = Colors.background,
        border = BorderStroke(Dimens.medium_stroke, color = Colors.border),
        contentPadding = PaddingValues(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            tint = Colors.primary,
            contentDescription = null
        )
    }
}

data class ItemFilterViewEntity(
    val id: String,
    val title: String,
    val isVisible: Boolean,
    val state: SingleSelectionRowFilterState
) {
    companion object {
        fun empty() = ItemFilterViewEntity("", "", false, SingleSelectionRowFilterState.Idle)
    }
}

data class SingleSelectionRowFilterViewEntity(
    val items: List<ItemFilterViewEntity>
)

enum class SingleSelectionRowFilterState {
    Idle,
    Selected,
    Disable
}
