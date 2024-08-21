package org.interview.chat.ui.navigation

sealed class ChatDestinations(
    val route: String
) {

    data object GRAPH: ChatDestinations("chatgraph")

    data object CHATS: ChatDestinations("chats")

    data object CHAT: ChatDestinations("chat")

}