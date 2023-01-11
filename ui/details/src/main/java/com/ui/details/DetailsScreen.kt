package com.ui.details

import Colors
import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.core.extensions.formatDate
import com.core_ui.components.TopAppBar
import com.core_ui.theme.custom.Typography
import com.service.search.repository.Repository
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun DetailsScreen(
    data: Repository,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background),
        topBar = {
            TopAppBar(title = "Details", showAppBarContent = true, onBackAction = { onBack.invoke() }) {}
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Colors.background)
                .padding(top = Dimens.large_padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    .size(160.dp)
                    .shadow(1.dp, CircleShape),
                imageModel = data.owner.image
            )

            Text(
                modifier = Modifier
                    .padding(top = Dimens.large_padding)
                    .padding(horizontal = Dimens.large_padding),
                text = data.name,
                style = Typography.Label.medium,
                color = Colors.text
            )

            Text(
                modifier = Modifier
                    .padding(top = Dimens.medium_padding)
                    .padding(horizontal = Dimens.large_padding),
                text = data.description ?: "",
                style = Typography.Label.xsmall,
                color = Colors.text
            )

            Row(
                modifier = androidx.compose.ui.Modifier.padding(Dimens.medium_padding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .padding(top = Dimens.medium_padding)
                        .padding(horizontal = Dimens.large_padding),
                    text = "${stringResource(id = R.string.details_screen_creation_date)} ${data.createDate.formatDate()}",
                    style = Typography.Label.xsmall,
                    color = Colors.text
                )

                Text(
                    modifier = Modifier
                        .padding(top = Dimens.medium_padding)
                        .padding(horizontal = Dimens.large_padding),
                    text = "${stringResource(id = R.string.details_screen_last_update)} ${data.updateDate.formatDate()}",
                    style = Typography.Label.xsmall,
                    color = Colors.text
                )
            }
        }
    }
}