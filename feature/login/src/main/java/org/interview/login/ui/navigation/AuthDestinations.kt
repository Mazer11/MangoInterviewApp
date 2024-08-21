package org.interview.login.ui.navigation

sealed class AuthDestinations(
    val route: String
) {

    data object GRAPH: AuthDestinations("logingraph")

    data object AUTH: AuthDestinations("auth")

    data object REGISTRATION: AuthDestinations("registration")

}