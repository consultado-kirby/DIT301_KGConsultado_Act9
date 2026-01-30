package com.example.mobiledev_consultadokirby.data

data class Message(
    val id: String = "",
    val text: String = "",
    val senderEmail: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
