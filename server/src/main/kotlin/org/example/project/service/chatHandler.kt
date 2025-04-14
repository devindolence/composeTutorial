package org.example.project.service

import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.chat.Message
import org.example.project.ClientSession
import org.example.project.connections
import java.util.*

// todo refactor package modules
suspend fun DefaultWebSocketServerSession.chatHandler() {
    val clientId = UUID.randomUUID().toString()
    val clientSession = ClientSession(clientId, this)
    connections.add(clientSession)
    try {
        incoming.consumeEach { frame ->
            if (frame is Frame.Text) {
                val receivedText = frame.readText()
                val check = Json.decodeFromString<Message>(receivedText)
                println("수신 메시지 (클라이언트 $clientId): $check")
                connections
                    .filter { it.id != clientId }
                    .asSequence()
                    .forEach { client ->
                        try {
                            println("client id: ${client.id}, session: ${client.session}")
                            client.session.send(Frame.Text(receivedText))
                        } catch (e: Exception) {
                            println("에러 발생: ${e.localizedMessage}")
                        }
                    }
            }
        }
    } catch (e: Exception) {
        println("에러 발생: ${e.localizedMessage}")
    } finally {
        connections.remove(clientSession)
    }
}