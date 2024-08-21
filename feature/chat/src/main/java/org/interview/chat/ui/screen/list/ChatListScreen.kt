package org.interview.chat.ui.screen.list

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import org.interview.chat.R
import org.interview.chat.ui.common.ChatItem
import org.interview.chat.ui.screen.list.event.ChatListEvent
import org.interview.chat.ui.screen.list.state.ChatListState
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.theme.paddings

@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    state: ChatListState,
    onEvent: (ChatListEvent) -> Unit
) {

    Scaffold(
        modifier = modifier,
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.chats),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Icon(
                    imageVector = Icons.Default.Face,
                    contentDescription = "Go to profile",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onEvent(ChatListEvent.OpenProfile) }
                )
            }
        }
    ) { paddingValues ->
        AnimatedContent(
            targetState = state,
            label = "",
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) { target ->
            when (target) {
                is ChatListState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularLoading()
                    }
                }

                is ChatListState.Ready -> {
                    val chats = remember { mutableStateOf(target.chats) }

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (chats.value.isNotEmpty())
                            items(
                                items = chats.value,
                                key = { it.name }
                            ) {
                                ChatItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    chatInfo = it,
                                    onChatClick = { onEvent(ChatListEvent.OpenChat(it.id.toInt())) },
                                    onDeleteChat = {
                                        chats.value = chats.value.filter { chat -> chat.name != it.name }
                                        onEvent(ChatListEvent.DeleteChat(it.id.toInt()))
                                    }
                                )
                            }
                        else
                            item {
                                Text(
                                    text = "Список чатов пуст",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(MaterialTheme.paddings.medium)
                                )
                            }

                    }
                }
            }
        }
    }

}