package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.chat.ChatAppWithScaffold

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
        ChatAppWithScaffold()
    }
}