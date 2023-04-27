package com.ui.details

import Colors
import Dimens
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.core.theme.custom.Typography
import com.core.theme.custom.whiteFFFFFF
import com.service.search.repository.Recipe

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowDetailsDialog(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    showDialog: MutableState<Boolean>
) {
    Dialog(onDismissRequest = { showDialog.value = false }) {
        Card(
            modifier = Modifier.padding(vertical = Dimens.xxxlarge_padding),
            shape = RoundedCornerShape(Dimens.bigger_biggest_corner_radius),
            elevation = 8.dp
        ) {
            Column(
                modifier
                    .background(Colors.background)
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = Dimens.small_padding, end = Dimens.small_padding),
                    shape = CircleShape,
                    border = BorderStroke(0.5.dp, Colors.border),
                    elevation = 2.dp,
                    onClick = { showDialog.value = false }
                ) {
                    Icon(
                        modifier = Modifier
                            .size(30.dp)
                            .padding(Dimens.xsmall_padding),
                        painter = painterResource(com.core.R.drawable.ic_close),
                        tint = Colors.primary,
                        contentDescription = ""
                    )
                }

                LazyColumn(
                    modifier
                        .background(Colors.background)
                        .padding(bottom = Dimens.large_padding)
                ) {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = Dimens.medium_padding)
                                .padding(horizontal = Dimens.medium_padding),
                            text = stringResource(id = R.string.details_screen_ingredients_label),
                            style = Typography.Label.large,
                            color = whiteFFFFFF
                        )
                    }

                    items(recipe.ingredients) { item ->
                        Text(
                            modifier = Modifier
                                .padding(top = Dimens.xsmall_padding)
                                .padding(horizontal = Dimens.medium_padding),
                            text = item.description,
                            style = Typography.Label.xsmall,
                            color = whiteFFFFFF
                        )
                    }

                    item {
                        Text(
                            modifier = Modifier
                                .padding(top = Dimens.large_padding)
                                .padding(horizontal = Dimens.medium_padding),
                            text = stringResource(id = R.string.details_screen_preparation_label),
                            style = Typography.Label.large,
                            color = whiteFFFFFF
                        )
                    }

                    items(recipe.details) { item ->
                        Text(
                            modifier = Modifier
                                .padding(top = Dimens.xsmall_padding)
                                .padding(horizontal = Dimens.medium_padding),
                            text = item.description,
                            style = Typography.Label.xsmall,
                            color = whiteFFFFFF
                        )
                    }
                }
            }
        }
    }
}