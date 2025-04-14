package org.example.project.module

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureJWT() {
    authentication {
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
}