package org.example.project.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import org.example.project.chat.Message
import org.example.project.ClientSession
import org.example.project.connections
import java.util.*

fun Application.chatRoute() {
    routing {
        // 채팅 페이지를 제공하는 HTML 반환 엔드포인트
        get("/chatPage") {
            // login 리다이렉트 시 URL에 포함된 JWT 토큰을 그대로 사용
            val htmlContent = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>채팅 페이지</title>
                    <script>
                        document.addEventListener("DOMContentLoaded", function() {
                            // URL 쿼리에서 토큰 파라미터 추출
                            const params = new URLSearchParams(window.location.search);
                            const token = params.get('token');
                            if (!token) {
                                alert("토큰이 없습니다. 로그인 페이지로 이동합니다.");
                                window.location.href = "/loginPage"; // 로그인 페이지 경로 설정
                                return;
                            }
                            // WebSocket 연결 시 토큰을 쿼리 파라미터로 포함시킵니다.
                            const ws = new WebSocket("ws://" + window.location.host + "/chatWS?token=" + encodeURIComponent(token));
                            ws.onopen = function() {
                                console.log("WebSocket 연결 성공");
                            };
                            ws.onmessage = function(event) {
                                console.log("수신된 메시지:", event.data);
                            };
                            ws.onerror = function(error) {
                                console.error("WebSocket 에러:", error);
                            };
                        });
                    </script>
                </head>
                <body>
                    <h1>채팅 페이지</h1>
                    <p>WebSocket 채팅이 시작됩니다.</p>
                </body>
                </html>
            """.trimIndent()
            call.respondText(htmlContent, ContentType.Text.Html)
        }

        // WebSocket 채팅 엔드포인트
        webSocket("/chat") {
            // 연결 요청 시 URL 쿼리 파라미터로부터 토큰 추출
            val token = call.request.queryParameters["token"]
            if (token == null) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "토큰이 없습니다"))
                return@webSocket
            }
            // JWT 토큰 검증
            val verifier = JWT.require(Algorithm.HMAC256("secret"))
                .withAudience("chat_audience")
                .withIssuer("ktor.io")
                .build()
            try {
                verifier.verify(token)
            } catch (ex: Exception) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "유효하지 않은 토큰입니다"))
                return@webSocket
            }

            // WebSocket 연결 처리
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
    }
}