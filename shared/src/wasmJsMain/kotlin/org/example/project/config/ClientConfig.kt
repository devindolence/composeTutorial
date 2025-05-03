package org.example.project.config

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

actual object HttpClientProvider {
    actual val client: HttpClient = HttpClient(Js) {
        install(ContentNegotiation) {
            json()
        }
        install(Auth) {
            bearer {
                loadTokens {
                    val token = TokenStorage.getToken() ?: ""
                    BearerTokens(token, "")
                }
            }
        }
    }
}