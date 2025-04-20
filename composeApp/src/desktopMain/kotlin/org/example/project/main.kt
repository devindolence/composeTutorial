package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.login.AuthScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KotlinProject",
    ) {
//        ChatAppWithScaffold()
        AuthScreen(
            onSuccess = { username, jwtToken ->
                println("로그인 성공: $username, JWT: $jwtToken")
            }
        )
    }
}