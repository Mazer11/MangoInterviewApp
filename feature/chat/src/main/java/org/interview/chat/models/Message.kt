package org.interview.chat.models

data class Message(
    val id: String,
    val text: String,
    val senderId: Int,
    val timestamp: String
)
