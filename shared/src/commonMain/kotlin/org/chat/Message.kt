package org.chat

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    val sender: String,
    val content: String
)