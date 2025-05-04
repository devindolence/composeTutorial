package org.example.project

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.CanvasBasedWindow
import org.example.project.chat.ChatAppWithScaffold
import org.example.project.config.ScreenState
import org.example.project.login.AuthScreen
import org.example.project.theme.Theme
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.configureWebResources

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    CanvasBasedWindow(canvasElementId = "root") {
        var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Login) }

        Column(modifier = Modifier.fillMaxSize()) {
            when (val state = screenState) {
                is ScreenState.Login -> AuthScreen { userId, jwtToken ->
                    screenState = ScreenState.Chat(userId, jwtToken)
                }

                is ScreenState.Chat -> ChatAppWithScaffold(username = state.userId, token = state.token)
            }
        }
    }
}