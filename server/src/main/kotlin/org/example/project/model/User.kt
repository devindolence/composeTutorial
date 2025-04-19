package org.example.project.model

import kotlinx.serialization.Serializable
import org.example.project.utils.LocalDateTimeSerializer
import java.time.LocalDateTime

@Serializable
data class User(
    val userId: Int,
    val username: String,
    val password: String,
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime,
) {
    constructor(username: String, password: String) : this(
        userId = 0,
        username = username,
        password = password,
        createdAt = LocalDateTime.now()
    )
}
