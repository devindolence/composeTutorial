package org.example.project.config

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object TokenStorage {
    private val _tokenFlow = MutableStateFlow<String?>(null)
    val tokenFlow: StateFlow<String?> = _tokenFlow

    fun saveToken(token: String) {
        _tokenFlow.value = token
    }

    fun getToken(): String? = _tokenFlow.value

    fun clear() {
        _tokenFlow.value = null
    }
}