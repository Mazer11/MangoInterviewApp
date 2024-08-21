package org.interview.chat.ui.screen.list.event

sealed interface ChatListEvent {

    data class DeleteChat(val chatId: Int): ChatListEvent

    data class OpenChat(val chatId: Int): ChatListEvent

    data object OpenProfile: ChatListEvent

}