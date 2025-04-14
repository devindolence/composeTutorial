package org.example.project.domain

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id").autoIncrement()
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 60)
    override val primaryKey = PrimaryKey(id)
}