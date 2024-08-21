package org.interview.login.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import org.interview.commonui.theme.paddings
import org.interview.login.ui.screen.auth.AuthScreen
import org.interview.login.ui.screen.registration.RegistrationScreen
import org.interview.login.ui.vm.AuthViewModel
import org.interview.login.ui.vm.RegistrationViewModel

fun NavGraphBuilder.AuthNavigation(
    navHostController: NavHostController,
    onLogged: () -> Unit
) {

    navigation(
        route = AuthDestinations.GRAPH.route,
        startDestination = AuthDestinations.AUTH.route
    ) {

        composable(route = AuthDestinations.AUTH.route) {
            val vm = hiltViewModel<AuthViewModel>()
            val state = vm.screenState.collectAsState()

            AuthScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                onEvent = vm::onEvent,
                onLogged = onLogged,
                onRegistration = { phone ->
                    navHostController.navigate(AuthDestinations.REGISTRATION.route + "/$phone")
                }
            )

        }

        composable(
            route = AuthDestinations.REGISTRATION.route + "/{phone}",
            arguments = listOf(
                navArgument("phone") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phone = backStackEntry.arguments?.getString("phone")
            val vm = hiltViewModel<RegistrationViewModel>()
            val state = vm.screenState.collectAsState()

            RegistrationScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                phone = phone ?: "",
                onEvent = vm::onEvent,
                onLogged = onLogged,
                onNavBack = { navHostController.navigateUp() }
            )

        }

    }

}