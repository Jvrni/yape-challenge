package com.core.components

import Colors
import Dimens
import YapeTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.theme.custom.*
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Ingredient(
    imageUrl: String,
    name: String,
) {
    Card(
        modifier = Modifier.width(60.dp).height(115.dp),
        backgroundColor = Colors.primary,
        shape = RoundedCornerShape(
            topStart = 30.dp,
            topEnd = 30.dp,
            bottomStart = Dimens.extra_biggest_corner_radius,
            bottomEnd = Dimens.extra_biggest_corner_radius
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            GlideImage(
                modifier = Modifier
                    .padding(top = Dimens.smedium_padding)
                    .size(40.dp)
                    .shadow(1.dp, CircleShape),
                imageModel = imageUrl
            )

            Text(
                modifier = Modifier
                    .padding(top = Dimens.smedium_padding)
                    .padding(bottom = Dimens.medium_padding)
                    .padding(horizontal = Dimens.xsmall_padding,),
                text = name,
                style = Typography.Label.xxsmall.copy(fontWeight = FontWeight.Bold),
                color = black282828,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
private fun prevFoodCard() {
    YapeTheme {
        Ingredient(
            imageUrl = "https://www.pngall.com/wp-content/uploads/2016/05/Pizza.png",
            name = "Tomatoe"
        )
    }
}