package org.example.project.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database

// database connection
fun initDatabase() {
    val config = HikariConfig().apply {
        jdbcUrl = "jdbc:postgresql://localhost:5432/chatdb"
        driverClassName = "org.postgresql.Driver"
        username = "myuser" // todo change env
        password = "secret"
        maximumPoolSize = 10
        // 필요한 경우 추가적인 Hikari 설정 적용
    }
    Database.connect(HikariDataSource(config))
}