package org.example.project.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.example.project.model.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// database connection
fun initDatabase() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/mydatabase" // todo change env
        driverClassName = "org.postgresql.Driver"
        username = "myuser" // todo change env
        password = "secret"
        maximumPoolSize = 10
        connectionTestQuery = "SELECT 1"
        connectionTimeout = 5000
        idleTimeout = 600000
        isAutoCommit = false
        schema = "public" // todo change env
        validate()
    }
    val database = Database.connect(
        datasource = HikariDataSource(config),
        databaseConfig = DatabaseConfig {
            sqlLogger = StdOutSqlLogger
        }
    )

    // 스키마 생성
    transaction(database) {
        addLogger(StdOutSqlLogger)
        println("Creating table # ${Users.tableName}")
        SchemaUtils.create(Users)
    }
}