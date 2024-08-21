package org.interview.mangointerviewapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.interview.chat.ui.navigation.ChatDestinations
import org.interview.chat.ui.navigation.ChatNavigation
import org.interview.login.ui.navigation.AuthDestinations
import org.interview.login.ui.navigation.AuthNavigation
import org.interview.profile.ui.navigation.ProfileDestinations
import org.interview.profile.ui.navigation.ProfileNavigation

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navHostController,
        modifier = modifier,
        startDestination = AuthDestinations.GRAPH.route
    ) {

        AuthNavigation(
            navHostController = navHostController,
            onLogged = {
                navHostController.navigate(ChatDestinations.GRAPH.route) {
                    popUpTo(AuthDestinations.GRAPH.route) {
                        inclusive = true
                    }
                }
            }
        )

        ChatNavigation(
            navHostController = navHostController,
            onOpenProfile = {
                navHostController.navigate(ProfileDestinations.GRAPH.route)
            }
        )

        ProfileNavigation(
            navHostController = navHostController,
            onLogout = {
                navHostController.navigate(AuthDestinations.GRAPH.route) {
                    popUpTo(ChatDestinations.GRAPH.route) {
                        inclusive = true
                    }
                }
            }
        )

    }

}