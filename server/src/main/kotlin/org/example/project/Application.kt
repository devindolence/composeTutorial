package org.example.project

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.chat.Message
import java.util.*
import kotlin.time.Duration.Companion.seconds

data class ClientSession(val id: String, val session: WebSocketSession)

val connections = Collections.synchronizedSet<ClientSession>(mutableSetOf())

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(WebSockets) {
            pingPeriod = 15.seconds
            timeout = 15.seconds
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        routing {
            chatHandler()
        }
    }.start(wait = true)
}

// todo refactor package modules
private fun Routing.chatHandler() {
    webSocket("/chat") { // 웹소켓 엔드포인트
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
                        .forEach {client ->
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
}