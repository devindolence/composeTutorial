package org.example.project.login

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import org.example.project.config.HttpClientProvider
import org.example.project.config.NetworkConfig

object AuthApi {
    val httpClient: HttpClient = HttpClientProvider.client
    val baseUrl: String = NetworkConfig.baseUrl

    @ExperimentalSerializationApi
    suspend fun login(
        username: String,
        password: String,
    ): AuthResponse {
        val response: HttpResponse = httpClient.submitForm(
            url = "$baseUrl/login-jwt",
            formParameters = Parameters.build {
                append("username", username)
                append("password", password)
            }
        )
        if (response.status == HttpStatusCode.OK) {
            return response.body()
        }
        throw RuntimeException("Login failed: ${response.status}")
    }
}

@Serializable
data class AuthResponse(
    val username: String,
    val token: String
)