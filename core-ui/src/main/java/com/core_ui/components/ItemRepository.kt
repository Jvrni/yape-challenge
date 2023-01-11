package com.core_ui.components

import Colors
import Dimens
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.core_ui.theme.custom.Typography
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ItemRepository(
    imageUrl: String,
    name: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(Dimens.large_padding).clickable { onClick.invoke() },
        verticalAlignment = Alignment.Top
    ) {

        GlideImage(
            modifier = Modifier
                .size(80.dp)
                .shadow(1.dp, CircleShape),
            imageModel = imageUrl
        )

        Column(
            modifier = Modifier.padding(start = Dimens.small_padding),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = Typography.Label.small,
                color = Colors.text
            )

            Text(
                text = description,
                style = Typography.Label.xxsmall,
                color = Colors.text
            )
        }
    }
}