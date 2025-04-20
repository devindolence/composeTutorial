import androidx.compose.ui.graphics.Color
import kotlinx.datetime.Clock
import org.jetbrains.compose.resources.DrawableResource
import kotlin.random.Random
import kotlin.random.nextInt

data class Message(
    val userProfile: UserProfile,
    val text: String,
    val seconds: Long,
    val id: Long
) {
    constructor(
        userProfile: UserProfile,
        text: String
    ) : this(
        userProfile = userProfile,
        text = text,
        seconds = Clock.System.now().epochSeconds,
        id = Random.nextLong()
    )
}

data class UserProfile(
    val name: String,
    val color: Color = ColorProvider.getColor(),
    val picture: DrawableResource?
)

object ColorProvider {
    val colors = mutableListOf(
        0xFFEA3468,
        0xFFB634EA,
        0xFF349BEA,
    )
    val allColors = colors.toList()
    fun getColor(): Color {
        if(colors.size == 0) {
            colors.addAll(allColors)
        }
        val idx = Random.nextInt(colors.indices)
        val color = colors[idx]
        colors.removeAt(idx)
        return Color(color)
    }
}