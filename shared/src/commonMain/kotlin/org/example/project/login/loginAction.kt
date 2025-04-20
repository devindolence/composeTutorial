package org.example.project.login

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

val httpClient: HttpClient = HttpClient {
    // Configure the client as needed
}

val baseUrl = "http://localhost:8080/api"

suspend fun loginAction(
    username: String,
    password: String,
    onSuccess: (String) -> Unit,
    onError: (String) -> Unit,
    setLoading: (Boolean) -> Unit
) {
    setLoading(true)
    try {
        val response: HttpResponse = httpClient.post("$baseUrl/api/login") {
            contentType(ContentType.Application.Json)
            setBody(mapOf("username" to username, "password" to password))
        }
        if (response.status == HttpStatusCode.OK) {
            val body = response.body<Map<String, String>>()
            onSuccess(body["userId"] ?: "")
        } else {
            onError("로그인 실패: ${response.status}")
        }
    } catch (e: Exception) {
        onError("네트워크 오류")
    } finally {
        setLoading(false)
    }
}