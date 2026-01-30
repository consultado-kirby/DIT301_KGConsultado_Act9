package com.example.mobiledev_consultadokirby.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mobiledev_consultadokirby.data.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ChatViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    
    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages
    
    private val _currentUser = MutableStateFlow(auth.currentUser?.email ?: "")
    val currentUser: StateFlow<String> = _currentUser
    
    init {
        listenToMessages()
    }
    
    fun signIn(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _currentUser.value = auth.currentUser?.email ?: ""
                onResult(true, "Login successful")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Login failed")
            }
        }
    }
    
    fun signUp(email: String, password: String, onResult: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _currentUser.value = auth.currentUser?.email ?: ""
                onResult(true, "Account created successfully")
            } catch (e: Exception) {
                onResult(false, e.message ?: "Sign up failed")
            }
        }
    }
    
    fun signOut() {
        auth.signOut()
        _currentUser.value = ""
    }
    
    fun sendMessage(text: String) {
        viewModelScope.launch {
            try {
                val message = hashMapOf(
                    "text" to text,
                    "senderEmail" to (auth.currentUser?.email ?: "Anonymous"),
                    "timestamp" to System.currentTimeMillis()
                )
                firestore.collection("messages").add(message).await()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
    
    private fun listenToMessages() {
        firestore.collection("messages")
            .orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, _ ->
                snapshot?.let {
                    val messageList = it.documents.mapNotNull { doc ->
                        Message(
                            id = doc.id,
                            text = doc.getString("text") ?: "",
                            senderEmail = doc.getString("senderEmail") ?: "",
                            timestamp = doc.getLong("timestamp") ?: 0L
                        )
                    }
                    _messages.value = messageList
                }
            }
    }
}
