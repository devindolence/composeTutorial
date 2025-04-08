package org.example.project

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun hello(): String {
        return "Hello, Open with ${platform.name}!"
    }
}