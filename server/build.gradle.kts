plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    kotlin("plugin.serialization") version "2.1.20"
    application
}

group = "org.example.project"
version = "1.0.0"

application {
    mainClass.set("org.example.project.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${extra["io.ktor.development"] ?: "false"}")
}

dependencies {
    implementation(projects.shared)
    implementation(libs.logback)
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    implementation(libs.ktor.server.websockets)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation("io.ktor:ktor-server-cors:3.1.2")

    testImplementation(libs.junit)
    testImplementation(libs.ktor.server.core)
    testImplementation(libs.ktor.server.websockets)
    testImplementation(libs.ktor.serialization.kotlinx.json)
    testImplementation(libs.ktor.client.content.negotiation)

    // Ktor
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")
    implementation("io.ktor:ktor-server-auth:${ktor}")
    implementation("io.ktor:ktor-server-auth-jwt:${ktor}")
    implementation("io.ktor:ktor-serialization-jackson:${ktor}")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.3.13")

    // Kotest
    testImplementation(libs.ktor.server.tests)         // ktor-server-tests-jvm
    testImplementation("io.ktor:ktor-server-test-host:2.3.0")

    // HikariCP와 Exposed
    implementation("com.zaxxer:HikariCP:6.2.1") // HikariCP 라이브러리
    implementation("org.jetbrains.exposed:exposed-core:0.59.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.59.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.59.0")
    implementation("org.jetbrains.exposed:exposed-java-time:0.59.0")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-datetime-jvm
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:0.6.1")
    implementation("org.postgresql:postgresql:42.7.5") // PostgreSQL 드라이버

    // 비밀번호 암호화를 위한 라이브러리 (예: BCrypt)
    implementation("org.mindrot:jbcrypt:0.4")
}

tasks.named<Tar>("distTar") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named<Zip>("distZip") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}