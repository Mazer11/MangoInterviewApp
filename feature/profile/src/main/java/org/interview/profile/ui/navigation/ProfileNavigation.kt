package org.interview.profile.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.interview.commonui.extensions.sharedViewModel
import org.interview.profile.ui.screen.edit.EditProfileScreen
import org.interview.profile.ui.screen.edit.event.EditProfileScreenEvent
import org.interview.profile.ui.screen.profile.ProfileScreen
import org.interview.profile.ui.screen.profile.event.ProfileScreenEvent
import org.interview.profile.ui.vm.ProfileViewModel

fun NavGraphBuilder.ProfileNavigation(
    navHostController: NavHostController,
    onLogout: () -> Unit
) {

    navigation(
        startDestination = ProfileDestinations.PROFILE.route,
        route = ProfileDestinations.GRAPH.route
    ) {

        composable(route = ProfileDestinations.PROFILE.route) { backStack ->
            val vm = backStack.sharedViewModel<ProfileViewModel>(navController = navHostController)
            val state = vm.profileState.collectAsState()

            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                onEvent = { event ->
                    when (event) {
                        is ProfileScreenEvent.EditProfile -> {
                            navHostController.navigate(ProfileDestinations.EDIT_PROFILE.route)
                        }

                        is ProfileScreenEvent.NavBack -> {
                            navHostController.navigateUp()
                        }

                        is ProfileScreenEvent.LogOut -> { onLogout() }
                    }
                }
            )
        }

        composable(route = ProfileDestinations.EDIT_PROFILE.route) { backStack ->
            val vm = backStack.sharedViewModel<ProfileViewModel>(navController = navHostController)
            val state = vm.editProfileState.collectAsState()

            EditProfileScreen(
                modifier = Modifier.fillMaxSize(),
                state = state.value,
                onEvent = { event ->
                    when(event){
                        is EditProfileScreenEvent.NavBack -> {
                            vm.onEditProfileEvent(EditProfileScreenEvent.NavBack)
                            navHostController.navigateUp()
                        }

                        else -> {
                            vm.onEditProfileEvent(event)
                        }
                    }
                }
            )
        }

    }

}