package com.example.mobiledev_consultadokirby.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignIn: (String, String, (Boolean, String) -> Unit) -> Unit,
    onSignUp: (String, String, (Boolean, String) -> Unit) -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Firebase Chat",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        errorMessage = ""
                    },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading
                )
                
                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        errorMessage = ""
                    },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    enabled = !isLoading
                )
                
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(40.dp))
                } else {
                    Button(
                        onClick = {
                            isLoading = true
                            onSignIn(email, password) { success, message ->
                                isLoading = false
                                if (success) {
                                    onLoginSuccess()
                                } else {
                                    errorMessage = message
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = email.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text("Sign In", modifier = Modifier.padding(vertical = 4.dp))
                    }
                    
                    OutlinedButton(
                        onClick = {
                            isLoading = true
                            onSignUp(email, password) { success, message ->
                                isLoading = false
                                if (success) {
                                    onLoginSuccess()
                                } else {
                                    errorMessage = message
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = email.isNotEmpty() && password.isNotEmpty()
                    ) {
                        Text("Create Account", modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}
