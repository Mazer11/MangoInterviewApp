package org.interview.login.ui.screen.registration

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.theme.paddings
import org.interview.login.models.RegistrationData
import org.interview.login.ui.screen.registration.event.RegistrationScreenEvents
import org.interview.login.ui.screen.registration.state.RegistrationScreenState
import org.interview.login.ui.screen.registration.state.RegistrationFormPage

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    state: RegistrationScreenState,
    phone: String,
    onEvent: (RegistrationScreenEvents) -> Unit,
    onLogged: () -> Unit,
    onNavBack: () -> Unit
) {
    val formData = remember { mutableStateOf(RegistrationData(phone, "", "")) }

    AnimatedContent(
        targetState = state,
        label = "RegistrationScreen state animation",
        modifier = modifier
    ) { target ->
        when (target) {
            is RegistrationScreenState.Form -> {
                RegistrationFormPage(
                    modifier = Modifier
                        .padding(MaterialTheme.paddings.medium)
                        .fillMaxSize(),
                    formData = formData,
                    errorMessage = target.errorMessage,
                    onSendData = { onEvent(RegistrationScreenEvents.SendRegistrationData(formData.value)) },
                    onNavBack = onNavBack
                )
            }

            is RegistrationScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularLoading()
                }
            }

            is RegistrationScreenState.Logged -> {
                onLogged()
            }
        }
    }

}