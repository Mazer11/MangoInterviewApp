package org.interview.login.ui.screen.registration.state

sealed interface RegistrationScreenState {

    data object Loading : RegistrationScreenState

    data class Form(val errorMessage: String? = null) : RegistrationScreenState

    data object Logged: RegistrationScreenState

}