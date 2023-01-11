package com.core_ui.theme.custom

import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color


val green1CAE81 = Color(0xFF1CAE81)
val black202020 = Color(0xFF202020)
val black000000 = Color(0xFF000000)
val black444444 = Color(0xFF444444)
val black161616 = Color(0xFF161616)
val whiteFFFFFF = Color(0xFFFFFFFF)
val gray888888 = Color(0xFF888888)
val grayE5E5E9 = Color(0xFFE5E5E9)
val grayF7FAFC = Color(0xFFF7FAFC)
val gray100 = Color(0xFFEEEEEE)
val gray400 = Color(0xFFAFAFAF)

val LightThemeColors = BankuishTestColors(
    primary = green1CAE81,
    tertiary = black000000,
    background = whiteFFFFFF,
    backgroundInput = grayF7FAFC,
    buttonText = whiteFFFFFF,
    buttonDisable = gray100,
    text = black000000,
    unSelectText = gray888888,
    border = grayE5E5E9,
    borderOpaque = gray400,
    isDark = false
)

val DarkThemeColors = BankuishTestColors(
    primary = green1CAE81,
    tertiary = whiteFFFFFF,
    background = black202020,
    backgroundInput = black161616,
    buttonText = whiteFFFFFF,
    buttonDisable = gray100,
    text = whiteFFFFFF,
    unSelectText = gray888888,
    border = black444444,
    borderOpaque = gray400,
    isDark = true
)

@Stable
class BankuishTestColors(
    primary: Color,
    tertiary: Color,
    background: Color,
    backgroundInput: Color,
    buttonText: Color,
    buttonDisable: Color,
    text: Color,
    unSelectText: Color,
    border: Color,
    borderOpaque: Color,
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
    var buttonText = mutableStateOf(buttonText).value
        private set
    var buttonDisable = mutableStateOf(buttonDisable).value
        private set
    var text = mutableStateOf(text).value
        private set
    var unSelectText = mutableStateOf(unSelectText).value
        private set
    var border = mutableStateOf(border).value
        private set
    var borderOpaque = mutableStateOf(borderOpaque).value
        private set
    var isDark = mutableStateOf(isDark).value
        private set

    fun copy(): BankuishTestColors = BankuishTestColors(
        primary = primary,
        tertiary = tertiary,
        background = background,
        backgroundInput = backgroundInput,
        buttonText = buttonText,
        buttonDisable = buttonDisable,
        text = text,
        unSelectText = unSelectText,
        border = border,
        borderOpaque = borderOpaque,
        isDark = isDark
    )

    fun update(other: BankuishTestColors) {
        primary = other.primary
        tertiary = other.tertiary
        background = other.background
        backgroundInput = other.backgroundInput
        buttonText = other.buttonText
        buttonDisable = other.buttonDisable
        text = other.text
        unSelectText = other.unSelectText
        border = other.border
        borderOpaque = other.borderOpaque
        isDark = other.isDark
    }
}