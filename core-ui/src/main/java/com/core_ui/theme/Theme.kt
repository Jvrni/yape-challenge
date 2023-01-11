import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.core_ui.theme.custom.*

private val LocalColors = staticCompositionLocalOf<BankuishTestColors> { error("No Colors provided") }
private val LocalDimens = staticCompositionLocalOf<Dimensions> { error("No Dimens provided") }

val Colors: BankuishTestColors
    @Composable
    get() = LocalColors.current

val Dimens: Dimensions
    @Composable
    get() = LocalDimens.current


@Composable
fun BankuishTestTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkThemeColors else LightThemeColors
    val rememberedColors = remember { colors.copy() }.apply { update(colors) }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalDimens provides DefaultDimensions
    ) {
        content.invoke()
    }
}
