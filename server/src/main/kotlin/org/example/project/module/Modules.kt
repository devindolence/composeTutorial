package org.example.project.module

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import org.example.project.model.User
import org.example.project.routes.chatRoute
import org.example.project.routes.jwtLoginRoute
import org.example.project.service.UserService
import org.example.project.utils.hashPassword
import java.time.LocalDateTime
import kotlin.time.Duration.Companion.seconds

fun Application.authModule() {
    // JWT 인증 설치 및 설정
    install(Authentication) {
        jwt("jwt-auth") {
            realm = "chat-server"
            verifier(
                JWT.require(Algorithm.HMAC256("secret"))
                    .withAudience("chat_audience")
                    .withIssuer("ktor.io") // issuer 값을 적절히 수정
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains("chat_audience")) JWTPrincipal(credential.payload)
                else null
            }
        }
    }
    jwtLoginRoute() // login route
}

fun Application.webSocketModule() {
    install(WebSockets) {
        pingPeriod = 15.seconds
        timeout = 15.seconds
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    chatRoute() // chat session
}


fun Application.module() {
    val userService = UserService()

    // 예: 회원가입 라우트
    routing {
        post("/api/register") {
            val req = call.receive<User>()
            println("posted user name: ${req.username}")
            val userId = userService.createUser(
                User(
                    userId = 0,
                    username = req.username,
                    password = hashPassword(req.password),
                    createdAt = LocalDateTime.now()
                )
            )
            call.respond(status = HttpStatusCode.Created, message = userId)
        }
    }
}