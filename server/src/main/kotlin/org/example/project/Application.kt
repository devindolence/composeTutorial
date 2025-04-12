package org.example.project

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import java.util.*
import kotlin.time.Duration.Companion.seconds

val connections = Collections.synchronizedSet<WebSocketSession>(mutableSetOf())

fun main() {
    embeddedServer(Netty, port = 8080) {
        install(WebSockets) {
            pingPeriod = 15.seconds
            timeout = 15.seconds
            maxFrameSize = Long.MAX_VALUE
            masking = false
        }
        routing {
            webSocket("/chat") { // 웹소켓 엔드포인트
                connections.add(this)
                send("서버에 접속하셨습니다.")
                // 클라이언트로부터 받은 메시지 브로드캐스팅 예제
                try {
                    incoming.consumeEach { frame ->
                        if (frame is Frame.Text) {
                            val receivedText = frame.readText()
                            println("수신 메시지 (클라이언트 ${this.hashCode()}): $receivedText")
                            connections.forEach { session ->
                                session.send(Frame.Text(receivedText))
                            }
                        }
                    }
                } catch (e: Exception) {
                    println("에러 발생: ${e.localizedMessage}")
                } finally {
                    connections.remove(this)
                }
            }
        }
    }.start(wait = true)
}