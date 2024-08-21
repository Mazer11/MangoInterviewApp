package org.interview.chat.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.interview.chat.ui.screen.chat.ChatScreen
import org.interview.chat.ui.screen.chat.event.ChatScreenEvent
import org.interview.chat.ui.screen.list.ChatListScreen
import org.interview.chat.ui.screen.list.event.ChatListEvent
import org.interview.chat.ui.vm.ChatListViewModel
import org.interview.chat.ui.vm.ChatViewModel

fun NavGraphBuilder.ChatNavigation(
    navHostController: NavHostController,
    onOpenProfile: () -> Unit
) {

    navigation(
        startDestination = ChatDestinations.CHATS.route,
        route = ChatDestinations.GRAPH.route
    ) {

        composable(route = ChatDestinations.CHATS.route) {
            val vm = hiltViewModel<ChatListViewModel>()
            val state = vm.state.collectAsState()

            ChatListScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                onEvent = {
                    when (it) {
                        is ChatListEvent.OpenProfile -> {
                            onOpenProfile()
                        }

                        is ChatListEvent.OpenChat -> {
                            navHostController.navigate(ChatDestinations.CHAT.route + "/${it.chatId}")
                        }

                        else -> vm.onEvent(it)
                    }
                }
            )

        }

        composable(
            route = ChatDestinations.CHAT.route + "/{chatId}",
            arguments = listOf(navArgument("chatId") { type = NavType.IntType })
        ) {
            val vm = hiltViewModel<ChatViewModel>()
            val state = vm.state.collectAsState()
            val chatId = it.arguments?.getInt("chatId")
            if (chatId != null)
                vm.onEvent(ChatScreenEvent.OpenChat(chatId.toString()))

            ChatScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                onEvent = vm::onEvent,
                onNavBack = { navHostController.navigateUp() }
            )
        }

    }

}