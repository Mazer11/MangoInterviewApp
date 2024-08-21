package org.interview.chat.ui.screen.chat.state

import org.interview.chat.models.ChatData
import org.interview.chat.models.Message

sealed interface ChatScreenState {

    data object Loading: ChatScreenState

    data class Ready(
        val chatInfo: ChatData,
        val messages: List<Message>,
        val userId: Int
    ): ChatScreenState

}