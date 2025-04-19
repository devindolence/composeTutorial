package org.example.project

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.websocket.*
import org.example.project.config.initDatabase
import org.example.project.module.authModule
import org.example.project.module.module
import org.example.project.module.webSocketModule
import java.util.*

data class ClientSession(val id: String, val session: WebSocketSession)

val connections = Collections.synchronizedSet<ClientSession>(mutableSetOf())

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
    ) {
        install(ContentNegotiation) {
            // Configure content negotiation
            json()
        }
        initDatabase()
        modules()
    }.start(wait = true)
}

fun Application.modules() {
    authModule()
    webSocketModule()
    module()
}