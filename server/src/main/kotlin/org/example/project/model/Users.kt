package org.example.project.model

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Users : IntIdTable("users") {
    val userId = integer(name = "user_id").autoIncrement().uniqueIndex()
    val username = varchar(name = "username", length = 50)
    val password = varchar(name = "password", length = 60)
    val createdAt = datetime("created_at").clientDefault { LocalDateTime.now() }
}