package com.core.theme.custom

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.core.theme.custom.Typography.Font.Montserrat
import com.core.R

sealed class Typography {
    object Font {
        val Montserrat = FontFamily(
            Font(R.font.montserrat_light, FontWeight.W300),
            Font(R.font.montserrat_regular, FontWeight.W400),
            Font(R.font.montserrat_medium, FontWeight.W500),
            Font(R.font.montserrat_semibold, FontWeight.W600),
            Font(R.font.montserrat_bold, FontWeight.W700)
        )
    }

    object Label {
        val large = TextStyle(fontSize = 21.sp, lineHeight = 24.sp, fontFamily = Montserrat, fontWeight = FontWeight.W600)
        val medium = TextStyle(fontSize = 18.sp, lineHeight = 24.sp, fontFamily = Montserrat, fontWeight = FontWeight.W500)
        val small = TextStyle(fontSize = 16.sp, lineHeight = 20.sp, fontFamily = Montserrat, fontWeight = FontWeight.W600)
        val xsmall = TextStyle(fontSize = 13.sp, lineHeight = 16.sp, fontFamily = Montserrat, fontWeight = FontWeight.W500)
        val xxsmall = TextStyle(fontSize = 11.sp, lineHeight = 16.sp, fontFamily = Montserrat, fontWeight = FontWeight.W400)
    }
}
