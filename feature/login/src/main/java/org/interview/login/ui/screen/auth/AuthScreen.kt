package org.interview.login.ui.screen.auth

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
import androidx.compose.ui.platform.LocalContext
import org.interview.commonui.composable.CircularLoading
import org.interview.commonui.theme.paddings
import org.interview.login.R
import org.interview.login.ui.screen.auth.event.AuthScreenEvents
import org.interview.login.ui.screen.auth.state.AuthCodePage
import org.interview.login.ui.screen.auth.state.AuthPhonePage
import org.interview.login.ui.screen.auth.state.AuthScreenState

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthScreenState,
    onEvent: (AuthScreenEvents) -> Unit,
    onLogged: () -> Unit,
    onRegistration: (String) -> Unit
) {
    val context = LocalContext.current

    AnimatedContent(
        targetState = state,
        label = "RegistrationScreen state animation",
        modifier = modifier
    ) { target ->
        when (target) {
            is AuthScreenState.Code -> {
                AuthCodePage(
                    phone = target.phone,
                    onCheckCode = { onEvent(AuthScreenEvents.CheckAuthCode(it)) },
                    errorMessage = target.errorMessage,
                    onNavBack = { onEvent(AuthScreenEvents.ReturnToSendPage) }
                )
            }

            is AuthScreenState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularLoading()
                }
            }

            is AuthScreenState.Logged -> {
                onLogged()
            }

            is AuthScreenState.Start -> {
                val errorMessage = remember { mutableStateOf(target.errorMessage) }

                AuthPhonePage(
                    modifier = Modifier
                        .padding(MaterialTheme.paddings.medium)
                        .fillMaxSize(),
                    onSendCode = { phone -> onEvent(AuthScreenEvents.SendAuthCode(phone)) },
                    errorMessage = errorMessage.value,
                    onRegistration = { phone ->
                        if (phone.isNullOrEmpty()) {
                            errorMessage.value = context.getString(R.string.enter_phone_to_register)
                        } else {
                            onRegistration(phone)
                        }
                    }
                )
            }
        }
    }

}