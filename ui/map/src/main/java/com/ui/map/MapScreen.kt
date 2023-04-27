package com.ui.map

import Colors
import Dimens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.core.theme.custom.Typography
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.core.theme.custom.*
import com.core.components.Button
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.service.search.repository.Location

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    location: Location,
    onBack: () -> Unit,
    onAction: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.background)
    ) {
        val (topAppBar, box, button) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(box) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(button.top)
                }
                .fillMaxWidth()
                .background(
                    black444444, shape = RoundedCornerShape(
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                    )
                )
                .clip(
                    RoundedCornerShape(
                        bottomEnd = 50.dp,
                        bottomStart = 50.dp
                    )
                )
        ) {
            val singapore = LatLng(location.lat, location.long)
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(singapore, 18f)
            }

            GoogleMap(
                modifier = Modifier.fillMaxWidth(),
                cameraPositionState = cameraPositionState
            ) {
                Marker(state = MarkerState(position = singapore))
            }
        }

        Card(
            modifier = Modifier
                .constrainAs(topAppBar) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
                .padding(top = Dimens.xxlarge_padding, start = Dimens.medium_padding),
            shape = RoundedCornerShape(16.dp),
            elevation = 2.dp,
            backgroundColor = black444444,
            onClick = { onBack.invoke() }
        ) {
            Icon(
                modifier = Modifier
                    .size(45.dp)
                    .padding(Dimens.smedium_padding),
                imageVector = Icons.Default.ArrowBack,
                tint = Colors.primary,
                contentDescription = ""
            )
        }

        Button(
            modifier = Modifier
                .constrainAs(button) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(Dimens.xxlarge_padding + Dimens.xxlarge_padding),
            contentPadding = PaddingValues(Dimens.medium_padding),
            onClick = { onAction.invoke() }) {
            Text(
                text = stringResource(id = R.string.map_screen_button_map),
                style = Typography.Label.xsmall.copy(fontWeight = FontWeight.SemiBold),
                color = black282828
            )
        }
    }
}