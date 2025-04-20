package org.example.project.chat

import Action
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
import androidx.compose.ui.layout.ContentScale
import createStore
import kotlinproject.composeapp.generated.resources.Res
import kotlinproject.composeapp.generated.resources.background
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.example.project.theme.Theme
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
    val user = friends.random()

    LaunchedEffect(Unit) {
        ChatClient.startChat { message ->
            store.send(
                Action.SendMessage(
                    Message(getUser(myUser), message.content)
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
                        SendMessage(currentUser = user.pictureKey) { text ->
                            store.send(
                                Action.SendMessage(
                                    Message(getUser(user), text)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
