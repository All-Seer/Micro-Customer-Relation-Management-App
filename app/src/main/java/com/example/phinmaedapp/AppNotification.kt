package com.example.phinmaedapp

data class AppNotification(
    val id: String = "",
    val title: String = "",
    val message: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val sender: String = "",
    val isRead: Boolean = false
)