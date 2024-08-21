package org.interview.profile.ui.navigation

sealed class ProfileDestinations(val route: String) {

    data object GRAPH: ProfileDestinations("profilegraph")

    data object PROFILE: ProfileDestinations("profile")

    data object EDIT_PROFILE: ProfileDestinations("editprofile")

}