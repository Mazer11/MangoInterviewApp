package org.interview.login.ui.screen.auth.state

sealed interface AuthScreenState {

    data object Loading : AuthScreenState

    data class Start(val errorMessage: String? = null) : AuthScreenState

    data class Code(val phone: String, val errorMessage: String? = null) : AuthScreenState

    data object Logged: AuthScreenState

}