package org.example.project.service

import kotlinx.coroutines.Dispatchers
import org.example.project.model.User
import org.example.project.model.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class UserService {
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) {
            addLogger(StdOutSqlLogger)
            block()
        }

    suspend fun createUser(user: User): Int =
        dbQuery {
            println("Create User: ${user.username}")
            Users.insert {
                it[Users.username] = user.username
                it[Users.password] = user.password
            } get Users.userId
            .also { println("User created with ID: $it") }
        }

    suspend fun getUserById(id: Int): User? =
        dbQuery {
            Users.selectAll()
                .where { Users.userId eq id }
                .map { toUser(it) }
                .singleOrNull()
        }

    suspend fun findByUsername(username: String): User? =
        dbQuery {
            Users.selectAll()
                .where { Users.username eq username }
                .map { toUser(it) }
                .singleOrNull()
        }

    private fun toUser(row: ResultRow) = User(
        userId = row[Users.userId],
        username = row[Users.username],
        password = row[Users.password],
        createdAt = row[Users.createdAt]
    )
}
