package org.interview.chat.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.interview.chat.models.ChatData
import org.interview.chat.ui.screen.list.event.ChatListEvent
import org.interview.chat.ui.screen.list.state.ChatListState
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<ChatListState>(ChatListState.Loading)
    val state: StateFlow<ChatListState> = _state.asStateFlow()

    //With real use cases I'd better use coroutine flow instead of init,
    // but it takes time to implement use cases,repository, etc.
    init {
        loadChats()
    }

    fun onEvent(event: ChatListEvent) {
        when (event) {
            is ChatListEvent.DeleteChat -> {
                deleteChat(event.chatId.toString())
            }

            is ChatListEvent.OpenChat -> {}
            ChatListEvent.OpenProfile -> {}
        }
    }

    private fun loadChats() {
        viewModelScope.launch {
            val chats = buildList {
                for (i in 0..20) {
                    add(
                        ChatData(
                            id = i.toString(),
                            name = "Chat $i"
                        )
                    )
                }
            }

            //mock delay
            delay(500)

            _state.value = ChatListState.Ready(chats)
        }
    }

    private fun deleteChat(chatId: String) {}

}