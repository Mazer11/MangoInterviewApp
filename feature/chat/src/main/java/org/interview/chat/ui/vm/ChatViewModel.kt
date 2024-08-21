package org.interview.chat.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.interview.chat.models.ChatData
import org.interview.chat.models.Message
import org.interview.chat.ui.screen.chat.event.ChatScreenEvent
import org.interview.chat.ui.screen.chat.state.ChatScreenState
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<ChatScreenState>(ChatScreenState.Loading)
    val state: StateFlow<ChatScreenState> = _state.asStateFlow()

    fun onEvent(event: ChatScreenEvent) {
        when (event) {
            is ChatScreenEvent.SendMessage -> {
                sendMessage(event.message)
            }

            is ChatScreenEvent.OpenChat -> {
                loadMessages(event.chatId)
            }
        }
    }

    private fun loadMessages(chatId: String) {
        viewModelScope.launch {
            val mockMessages = buildList {
                for (i in 0..20) {
                    add(
                        Message(
                            id = i.toString(),
                            text = "Message $i",
                            senderId = if (i % 4 == 0) 1 else 2,
                            timestamp = "12:00"
                        )
                    )
                }
            }

            //Mock load delay
            delay(500)

            _state.value = ChatScreenState.Ready(
                messages = mockMessages,
                chatInfo = ChatData(
                    name = "Chat Name $chatId",
                    id = chatId
                ),
                userId = 1
            )
        }
    }

    private fun sendMessage(message: String) {}

}