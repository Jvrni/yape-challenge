package com.core.components

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
import com.core.theme.custom.Typography

@Composable
fun EmptyState(
    @DrawableRes image: Int = com.core.R.drawable.img_empty,
    @StringRes title: Int = com.core.R.string.title_empty_screen,
    @StringRes description: Int = com.core.R.string.description_empty_screen,
    @StringRes buttonText: Int = com.core.R.string.button_empty_screen,
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