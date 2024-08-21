package org.interview.chat.ui.screen.list.state

import org.interview.chat.models.ChatData

sealed interface ChatListState {

    data object Loading: ChatListState

    data class Ready(val chats: List<ChatData>): ChatListState

}