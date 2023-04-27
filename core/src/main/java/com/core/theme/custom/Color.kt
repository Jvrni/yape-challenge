package com.core.theme.custom

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


val yellowF8C929 = Color(0xFFF8C929)
val black0D0D0D = Color(0xFF0D0D0D)
val black282828 = Color(0xFF282828)
val black444444 = Color(0xFF444444)
val whiteFFFFFF = Color(0xFFFFFFFF)
val gray818181 = Color(0xFF818181)
val grayF7FAFC = Color(0xFFF7FAFC)
val gray888888 = Color(0xFF888888)
val grayE5E5E9 = Color(0xFFE5E5E9)

val LightThemeColors = YapeColors(
    primary = yellowF8C929,
    tertiary = whiteFFFFFF,
    background = whiteFFFFFF,
    backgroundInput = whiteFFFFFF,
    text = black282828,
    unSelectText = gray888888,
    border = grayE5E5E9,
    isDark = false
)

val DarkThemeColors = YapeColors(
    primary = yellowF8C929,
    tertiary = black0D0D0D,
    background = black282828,
    backgroundInput = black282828,
    text = whiteFFFFFF,
    unSelectText = gray888888,
    border = grayE5E5E9,
    isDark = true
)

@Stable
class YapeColors(
    primary: Color,
    tertiary: Color,
    background: Color,
    backgroundInput: Color,
    text: Color,
    unSelectText: Color,
    border: Color,
    isDark: Boolean
) {
    var primary = mutableStateOf(primary).value
        private set
    var tertiary = mutableStateOf(tertiary).value
        private set
    var background = mutableStateOf(background).value
        private set
    var backgroundInput = mutableStateOf(backgroundInput).value
        private set
    var text = mutableStateOf(text).value
        private set
    var unSelectText = mutableStateOf(unSelectText).value
        private set
    var border = mutableStateOf(border).value
        private set
    var isDark = mutableStateOf(isDark).value
        private set

    fun copy(): YapeColors = YapeColors(
        primary = primary,
        tertiary = tertiary,
        background = background,
        backgroundInput = backgroundInput,
        text = text,
        unSelectText = unSelectText,
        border = border,
        isDark = isDark
    )

    fun update(other: YapeColors) {
        primary = other.primary
        tertiary = other.tertiary
        background = other.background
        backgroundInput = other.backgroundInput
        text = other.text
        unSelectText = other.unSelectText
        border = other.border
        isDark = other.isDark
    }
}