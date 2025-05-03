package org.example.project.config

sealed class ScreenState {
    object Login : ScreenState()
    data class Chat(val userId: String, val token: String) : ScreenState()
}