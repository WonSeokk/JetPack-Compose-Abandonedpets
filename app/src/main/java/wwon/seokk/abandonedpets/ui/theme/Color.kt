package wwon.seokk.abandonedpets.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import javax.annotation.concurrent.Immutable
/**
 * Created by WonSeok on 2022.08.15
 **/

val SurfaceColor = Color(0xFFFFFFFF)
val SurfaceVariantColor = Color(0xFFF2F4F6)
val GreenColor = Color(0xFF8BC34A)
val RedColor = Color(0xFFF44336)

@Immutable
data class AbandonedPetsColors(
    val surfaceColor: Color,
    val surfaceVariantColor: Color,
    val greenColor: Color,
    val redColor: Color
)

val LocalAbandonedPetsColors = staticCompositionLocalOf {
    AbandonedPetsColors (
        surfaceColor = Color.White,
        surfaceVariantColor = Color.LightGray,
        greenColor = Color.Green,
        redColor = Color.Red
    )
}
