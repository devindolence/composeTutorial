package org.example.project

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import org.example.project.routes.jwtLoginRoute
import org.example.project.service.chatHandler
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
        jwtLoginRoute() // login route
        routing {
            webSocket("/chat") { // 웹소켓 엔드포인트
                chatHandler()
            }
        }
    }.start(wait = true)
}