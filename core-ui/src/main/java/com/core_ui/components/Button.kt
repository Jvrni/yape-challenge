package com.core_ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import Colors

private val ButtonShape = RoundedCornerShape(percent = 50)

enum class ButtonState {
    Idle, Disable, Loading;

    fun isEnabled(): Boolean {
        return this == Idle
    }

    fun isLoading(): Boolean {
        return this == Loading
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonState: ButtonState = ButtonState.Idle,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = ButtonShape,
    border: BorderStroke? = null,
    backgroundColor: Color = Colors.primary,
    contentColor: Color = Colors.buttonText,
    disabledContentColor: Color = Colors.buttonDisable,
    loadingColor: Color = Colors.buttonText,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    content: @Composable RowScope.() -> Unit
) {
    val contentColorAnimated by animateColorAsState(
        if (buttonState.isEnabled()) contentColor else disabledContentColor
    )

    val backgroundColorAnimated by animateColorAsState(
        if (buttonState.isEnabled() || buttonState.isLoading()) backgroundColor
        else disabledContentColor
    )

    Surface(
        shape = shape,
        color = Color.Transparent,
        contentColor = contentColorAnimated,
        border = border,
        modifier = modifier
            .clip(shape)
            .background(backgroundColorAnimated)
            .clickable(
                onClick = onClick,
                enabled = buttonState.isEnabled(),
                role = Role.Button,
                interactionSource = interactionSource,
                indication = null
            )
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        if (buttonState.isLoading()) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .align(Alignment.Center),
                color = loadingColor,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                Modifier
                    .indication(interactionSource, rememberRipple())
                    .padding(contentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}
