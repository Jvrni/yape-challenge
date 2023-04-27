package com.ui.details

import Colors
import Dimens
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.core.components.TopAppBar
import com.core.theme.custom.*
import com.core.components.Button
import com.core.components.Ingredient
import com.core.theme.custom.Typography
import com.service.search.repository.Recipe
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DetailsScreen(
    recipe: Recipe,
    onBack: () -> Unit,
    onAction: () -> Unit
) {
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background),
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.details_screen_title),
                showAppBarContent = true,
                onBackAction = { onBack.invoke() }) {}
        }
    ) {
        if (showDialog.value)
            ShowDetailsDialog(recipe = recipe, showDialog = showDialog)

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Colors.background)
                .padding(top = Dimens.medium_padding),
        ) {
            val (image, column) = createRefs()

            GlideImage(
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .size(180.dp)
                    .shadow(1.dp, CircleShape),
                imageModel = recipe.image,
            )

            Column(
                modifier = Modifier
                    .constrainAs(column) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxSize()
                    .padding(top = Dimens.xlarge_padding)
                    .background(
                        black444444, shape = RoundedCornerShape(
                            topStart = 50.dp,
                            topEnd = 50.dp
                        )
                    )
                    .padding(horizontal = Dimens.large_padding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    modifier = Modifier
                        .padding(top = Dimens.xlarge_padding)
                        .width(40.dp)
                        .background(
                            gray818181,
                            RoundedCornerShape(Dimens.bigger_biggest_corner_radius)
                        ), thickness = 6.dp
                )

                Text(
                    modifier = Modifier.padding(top = Dimens.large_padding),
                    text = recipe.name,
                    style = Typography.Label.large,
                    color = whiteFFFFFF
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.large_padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = com.core.R.drawable.ic_time),
                            contentDescription = "",
                            tint = Colors.primary
                        )

                        Text(
                            modifier = Modifier.padding(top = Dimens.xsmall_padding),
                            text = stringResource(id = R.string.details_screen_minutes_label, recipe.timeMin),
                            style = Typography.Label.xsmall,
                            color = whiteFFFFFF
                        )

                        Text(
                            modifier = Modifier.padding(top = Dimens.xsmall_padding),
                            text = stringResource(id = R.string.details_screen_cooking_label),
                            style = Typography.Label.xxsmall,
                            color = whiteFFFFFF
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = Colors.primary
                        )

                        Text(
                            modifier = Modifier.padding(top = Dimens.xsmall_padding),
                            text = "${recipe.rating}",
                            style = Typography.Label.xsmall,
                            color = whiteFFFFFF
                        )

                        Text(
                            modifier = Modifier.padding(top = Dimens.xsmall_padding),
                            text = stringResource(id = R.string.details_screen_rating_label),
                            style = Typography.Label.xxsmall,
                            color = whiteFFFFFF
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.large_padding)
                        .background(
                            black282828,
                            shape = RoundedCornerShape(Dimens.bigger_biggest_corner_radius)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(start = Dimens.medium_padding),
                        text = stringResource(id = R.string.details_screen_ingredients_label),
                        style = Typography.Label.xsmall,
                        color = whiteFFFFFF
                    )

                    Button(
                        modifier = Modifier.padding(Dimens.smedium_padding),
                        onClick = { onAction.invoke() }) {
                        Text(
                            text = stringResource(id = R.string.details_screen_location_label),
                            style = Typography.Label.xsmall.copy(fontWeight = FontWeight.SemiBold),
                            color = black282828
                        )
                    }
                }

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.large_padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(Dimens.medium_padding),
                ) {
                    items(recipe.ingredients) { item ->
                        Ingredient(
                            imageUrl = item.image,
                            name = item.name
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(Dimens.large_padding)
                        .clickable { showDialog.value = true },
                    text = stringResource(id = R.string.details_screen_show_more_label),
                    style = Typography.Label.xsmall,
                    color = whiteFFFFFF
                )
            }
        }
    }
}