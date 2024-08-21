package org.interview.login.ui.screen.auth.event

sealed interface AuthScreenEvents {
    data class SendAuthCode(val phone: String): AuthScreenEvents
    data class CheckAuthCode(val code: String): AuthScreenEvents
    data object ReturnToSendPage: AuthScreenEvents
}