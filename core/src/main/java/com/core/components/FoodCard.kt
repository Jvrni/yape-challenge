package com.core.components

import Colors
import Dimens
import YapeTheme
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.core.theme.custom.*
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FoodCard(
    imageUrl: String,
    name: String,
    timeMin: Int,
    rating: Int = 2,
    onClick: () -> Unit
) {
    ConstraintLayout {
        val (card, image) = createRefs()

        Card(
            modifier = Modifier
                .constrainAs(card) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .padding(Dimens.medium_padding)
                .clip(RoundedCornerShape(Dimens.bigger_biggest_corner_radius))
                .clickable { onClick.invoke() },
            backgroundColor = black444444,
            shape = RoundedCornerShape(Dimens.bigger_biggest_corner_radius)
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = Dimens.xxlarge_padding,
                    horizontal = Dimens.large_padding
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Dimens.smedium_padding),
                    text = name,
                    style = Typography.Label.xsmall,
                    textAlign = TextAlign.Center,
                    color = whiteFFFFFF
                )

                Row {
                    repeat(5) {
                        Icon(
                            modifier = Modifier
                                .size(20.dp)
                                .padding(top = Dimens.xxsmall_padding),
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = if (it <= rating - 1) Colors.primary else gray888888
                        )
                    }
                }

                Row(
                    modifier = Modifier.padding(top = Dimens.large_padding),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(13.dp),
                        painter = painterResource(id = com.core.R.drawable.ic_time),
                        contentDescription = "",
                        tint = whiteFFFFFF
                    )

                    Text(
                        modifier = Modifier.padding(start = Dimens.xsmall_padding),
                        text = "$timeMin mins",
                        style = Typography.Label.xxsmall,
                        color = whiteFFFFFF
                    )
                }
            }
        }

        GlideImage(
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(card.top)
                    start.linkTo(card.start)
                    end.linkTo(card.end)
                    bottom.linkTo(card.top)
                }
                .padding(top = Dimens.medium_padding)
                .size(90.dp)
                .shadow(1.dp, CircleShape)
                .clip(CircleShape)
                .clickable { onClick.invoke() },
            imageModel = imageUrl,
        )
    }
}

@Preview
@Composable
private fun prevFoodCard() {
    YapeTheme {
        FoodCard(
            imageUrl = "https://www.pngall.com/wp-content/uploads/2016/05/Pizza.png",
            name = "Cheese Pizza",
            timeMin = 30
        ) {

        }
    }
}