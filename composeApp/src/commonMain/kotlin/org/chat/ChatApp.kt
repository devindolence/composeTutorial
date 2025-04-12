package org.chat

import Action
import ChatColors
import Message
import Messages
import SendMessage
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import createStore
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.background
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

val store = CoroutineScope(SupervisorJob()).createStore()

@Composable
@Preview
fun ChatAppWithScaffold(displayTextField: Boolean = true) {
    Theme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("The Composers Chat") },
                    backgroundColor = MaterialTheme.colors.background,
                )
            }) {
            ChatApp(displayTextField = displayTextField)
        }
    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChatApp(displayTextField: Boolean = true) {
    val state by store.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        ChatClient.startChat { message ->
            store.send(
                Action.SendMessage(
                    Message(getUser(friends[0]), message)
                )
            )
        }
    }

    Theme {
        Surface {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(painterResource(Res.drawable.background), null, contentScale = ContentScale.Crop)
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(Modifier.weight(1f)) {
                        Messages(state.messages)
                    }
                    if (displayTextField) {
                        SendMessage { text ->
                            store.send(
                                Action.SendMessage(
                                    Message(getUser(myUser), text)
                                )
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Theme(content: @Composable () -> Unit) {
    MaterialTheme(
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
