package org.interview.chat.ui.screen.chat

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.interview.chat.models.Message
import org.interview.chat.ui.common.ChatBottomBar
import org.interview.chat.ui.common.ChatMessage
import org.interview.chat.ui.screen.chat.event.ChatScreenEvent
import org.interview.chat.ui.screen.chat.state.ChatScreenState
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.theme.paddings

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    state: ChatScreenState,
    onEvent: (ChatScreenEvent) -> Unit,
    onNavBack: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    AnimatedContent(modifier = modifier, targetState = state, label = "") { target ->
        when (target) {
            is ChatScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularLoading()
                }
            }

            is ChatScreenState.Ready -> {
                val listSate = rememberLazyListState(target.messages.size)
                val messages = remember { mutableStateOf(target.messages) }

                Scaffold(
                    modifier = modifier,
                    topBar = {
                        Row(
                            modifier = Modifier
                                .padding(
                                    vertical = MaterialTheme.paddings.large,
                                    horizontal = MaterialTheme.paddings.medium
                                )
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Navigate Back",
                                tint = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.clickable { onNavBack() }
                            )

                            Text(
                                text = target.chatInfo.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(start = MaterialTheme.paddings.medium)
                            )
                        }
                    },
                    bottomBar = {
                        ChatBottomBar(
                            modifier = Modifier
                                .padding(MaterialTheme.paddings.small)
                                .fillMaxWidth(),
                            onSendMessage = {
                                onEvent(ChatScreenEvent.SendMessage(it))
                                messages.value += Message(
                                    id = (messages.value.size + 1).toString(),
                                    text = it,
                                    senderId = 1,
                                    timestamp = "12:00"
                                )
                                coroutineScope.launch {
                                    listSate.animateScrollToItem(messages.value.size - 1)
                                }
                            }
                        )
                    }
                ) { paddingValues ->

                    LazyColumn(
                        state = listSate,
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        items(items = messages.value, key = { it.id }) {
                            val isMine = it.senderId == target.userId

                            Box(
                                modifier = Modifier
                                    .padding(MaterialTheme.paddings.small)
                                    .fillMaxWidth(),
                                contentAlignment = if (isMine)
                                    Alignment.CenterEnd
                                else
                                    Alignment.CenterStart
                            ) {
                                ChatMessage(
                                    message = it.text,
                                    isMine = isMine,
                                    timeStamp = it.timestamp
                                )
                            }
                        }
                    }

                }
            }
        }
    }

}