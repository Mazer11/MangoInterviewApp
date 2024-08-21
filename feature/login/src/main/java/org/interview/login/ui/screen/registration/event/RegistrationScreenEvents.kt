package org.interview.login.ui.screen.registration.event

import org.interview.login.models.RegistrationData

sealed interface RegistrationScreenEvents {
    data class SendRegistrationData(val data: RegistrationData): RegistrationScreenEvents
}