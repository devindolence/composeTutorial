package org.example.project.theme

import ChatColors
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import kotlinproject.composeapp.generated.resources.NotoSansKR_Regular
import kotlinproject.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun Theme(content: @Composable () -> Unit) {
    val font = FontFamily(
        Font(Res.font.NotoSansKR_Regular),
    )
    MaterialTheme(
        typography = Typography(defaultFontFamily = font),
        colors = lightColors(
            surface = Color(ChatColors.SURFACE),
            background = Color(ChatColors.TOP_GRADIENT.last()),
        ),
    ) {
        ProvideTextStyle(LocalTextStyle.current.copy(letterSpacing = 0.sp)) {
            content()
        }
    }
}