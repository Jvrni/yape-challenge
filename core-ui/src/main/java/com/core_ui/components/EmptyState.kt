package com.core_ui.components

import Colors
import Dimens
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.core_ui.R
import com.core_ui.theme.custom.Typography

@Composable
fun EmptyState(
    @DrawableRes image: Int = R.drawable.img_empty,
    @StringRes title: Int = R.string.title_empty_screen,
    @StringRes description: Int = R.string.description_empty_screen,
    @StringRes buttonText: Int = R.string.button_empty_screen,
    onClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = Dimens.xxlarge_padding)
            .padding(horizontal = Dimens.large_padding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(painter = painterResource(id = image), contentDescription = "")

        Text(
            text = stringResource(id = title),
            style = Typography.Label.large,
            color = Colors.text
        )

        Text(
            text = stringResource(id = description),
            style = Typography.Label.xsmall,
            color = Colors.text
        )

        if (onClick != null)
            Button(
                modifier = Modifier.padding(vertical = Dimens.large_padding),
                contentPadding = PaddingValues(
                    horizontal = Dimens.large_padding,
                    vertical = Dimens.small_padding
                ),
                onClick = { onClick.invoke() }) {
                Text(
                    text = stringResource(id = buttonText),
                    style = Typography.Label.xsmall,
                    color = Colors.text
                )
            }
    }
}