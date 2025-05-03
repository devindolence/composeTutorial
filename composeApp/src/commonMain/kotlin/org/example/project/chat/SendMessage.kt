import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import org.example.project.chat.ChatClient
import org.example.project.chat.Message

@Composable
fun SendMessage(
    currentUser: String?,
    message: (String) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    TextField(
        modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .onKeyEvent { keyEvent: KeyEvent ->
                if (keyEvent.type == KeyEventType.KeyUp && keyEvent.key == Key.Enter) {
                    if (inputText.isNotEmpty()) {
                        val messageObj = Message(sender = currentUser!!, content = inputText)
                        ChatClient.sendMessage(messageObj)
                        message(inputText)
                        inputText = ""
                    }
                    true
                } else false
            }
            .padding(10.dp),
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.White),
        value = inputText,
        placeholder = {
            Text("Type message...")
        },
        onValueChange = {
            inputText = it
        },
        trailingIcon = {
            if (inputText.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .clickable {
                            val messageObj = Message(sender = currentUser!!, content = inputText)
                            ChatClient.sendMessage(messageObj)
                            message(inputText)
                            inputText = ""
                        }
                        .pointerHoverIcon(PointerIcon.Hand)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colors.primary
                    )
                    Text("Send")
                }
            }
        }
    )
}