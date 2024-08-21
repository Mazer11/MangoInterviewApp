package org.interview.chat.ui.screen.chat.event

sealed interface ChatScreenEvent {

    data class SendMessage(val message: String) : ChatScreenEvent

    data class OpenChat(val chatId: String) : ChatScreenEvent

}