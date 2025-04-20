package org.example.project.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.example.project.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun AuthScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(false) }

    Theme {
        Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center) {
            Text("로그인", style = MaterialTheme.typography.h4)
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Spacer(Modifier.height(16.dp))

            LoginButton(loading, error) {
                CoroutineScope(Dispatchers.Main).launch {
                    loginAction(username, password, { userId ->
                        // 성공 처리 로직
                        println("로그인 성공: $userId")
                    }, { errorMessage ->
                        error = errorMessage
                    }, { isLoading ->
                        loading = isLoading
                    })
                }
            }
            error?.let { Text(it, color = MaterialTheme.colors.error) }
        }
    }
}

@Composable
fun LoginButton(
    loading: Boolean,
    error: String?,
    action: () -> Unit
) {
    Button(
        onClick = { action() },
        enabled = !loading,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(20.dp))
        else Text("로그인")
    }
}

