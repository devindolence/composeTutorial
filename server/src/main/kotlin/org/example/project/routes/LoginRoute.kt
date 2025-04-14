package org.example.project.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.example.project.domain.Users
import org.example.project.utils.verifyPassword
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.jwtLoginRoute() {
    routing {
        post("/login-jwt") {
            val credentials = call.receiveParameters()
            val username = credentials["username"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Username required")
            val password = credentials["password"] ?: return@post call.respond(HttpStatusCode.BadRequest, "Password required")

            val user = transaction {
                Users.select { Users.username eq username }.singleOrNull()
            }
            if (user != null && verifyPassword(password, user[Users.password])) {
                val token = JWT.create()
                    .withAudience("chat_audience")
                    .withIssuer("ktor.io")
                    .withClaim("username", username)
                    .withClaim("userId", user[Users.id])
                    .sign(Algorithm.HMAC256("secret"))
                call.respond(mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "인증 실패")
            }
        }

        authenticate("jwt-auth") {
            get("/chat") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                call.respondText("JWT 인증된 채팅방에 오신 것을 환영합니다, $username!")
            }
        }
    }
}