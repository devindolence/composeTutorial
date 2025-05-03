package org.example.project.config

import io.ktor.client.*

expect object HttpClientProvider {
    // Configure the HTTP client here
    val client: HttpClient
}

object NetworkConfig {
    val baseUrl: String = "http://localhost:8080/api"
}