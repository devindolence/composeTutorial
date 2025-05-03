package org.example.project

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.example.project.chat.ChatAppWithScaffold
import org.example.project.config.ScreenState
import org.example.project.login.AuthScreen
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.configureWebResources

@OptIn(ExperimentalComposeUiApi::class, ExperimentalResourceApi::class)
fun main() {
    configureWebResources {
        resourcePathMapping { path -> "./$path" }
    }
    CanvasBasedWindow {
        var screenState by remember { mutableStateOf<ScreenState>(ScreenState.Login) }

        when (val state = screenState) {
            is ScreenState.Login -> AuthScreen { userId, jwtToken ->
                screenState = ScreenState.Chat(userId, jwtToken)
            }

            is ScreenState.Chat -> ChatAppWithScaffold(username = state.userId, token = state.token)
        }
    }
}