package org.example.project.chat

import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.config.TokenStorage

object ChatClient {
    private lateinit var session: DefaultClientWebSocketSession
    private val client = HttpClient {
        install(WebSockets)
    }

    fun startChat(onMessageReceived: (Message) -> Unit) {
        CoroutineScope(Dispatchers.Main).launch {
            session = client.webSocketSession(
                method = HttpMethod.Get,
                host = "localhost",
                port = 8080,
                path = "/chat",
                block = {
                    // JWT 토큰을 쿼리 파라미터로 추가
                    val token = TokenStorage.getToken()
                    if (token != null) {
                        url.parameters.append("token", token)
                    }
                }
            )
            launch {
                try {
                    for (frame in session.incoming) {
                        if (frame is Frame.Text) {
                            val readText = frame.readText()
                            val messageObj = Json.decodeFromString<Message>(readText)
                            println("Received message: $messageObj")
                            onMessageReceived(messageObj)
                        }
                    }
                } catch (e: Exception) {
                    println("Error: ${e.message}")
                }
            }
        }
    }

    fun sendMessage(message: Message) {
        CoroutineScope(Dispatchers.Main).launch {
            if (::session.isInitialized) {
                val messageJson = Json.encodeToString(message)
                println("Sending message: $messageJson")
                session.send(Frame.Text(messageJson))
            } else {
                println("세션이 활성화되지 않았습니다.")
            }
        }
    }

    fun stop() {
        client.close()
    }
}