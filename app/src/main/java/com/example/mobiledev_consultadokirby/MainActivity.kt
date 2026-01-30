package com.example.mobiledev_consultadokirby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobiledev_consultadokirby.ui.ChatScreen
import com.example.mobiledev_consultadokirby.ui.LoginScreen
import com.example.mobiledev_consultadokirby.ui.theme.MobileDevConsultadoKirbyTheme
import com.example.mobiledev_consultadokirby.viewmodel.ChatViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            MobileDevConsultadoKirbyTheme {
                ChatApp()
            }
        }
    }
}

@Composable
fun ChatApp(viewModel: ChatViewModel = viewModel()) {
    val currentUser by viewModel.currentUser.collectAsState()
    val messages by viewModel.messages.collectAsState()
    var isLoggedIn by remember { mutableStateOf(false) }
    
    LaunchedEffect(currentUser) {
        isLoggedIn = currentUser.isNotEmpty()
    }
    
    if (isLoggedIn) {
        ChatScreen(
            messages = messages,
            currentUserEmail = currentUser,
            onSendMessage = { text -> viewModel.sendMessage(text) },
            onLogout = {
                viewModel.signOut()
                isLoggedIn = false
            }
        )
    } else {
        LoginScreen(
            onLoginSuccess = { isLoggedIn = true },
            onSignIn = viewModel::signIn,
            onSignUp = viewModel::signUp
        )
    }
}