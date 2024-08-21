package org.interview.login.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.interview.login.models.CheckAuthData
import org.interview.login.models.SendAuthData
import org.interview.login.ui.screen.auth.event.AuthScreenEvents
import org.interview.login.ui.screen.auth.state.AuthScreenState
import org.interview.login.usecase.CheckAuthUseCase
import org.interview.login.usecase.SendAuthUseCase
import org.interview.remote.models.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sendAuthUseCase: SendAuthUseCase,
    private val checkAuthUseCase: CheckAuthUseCase
) : ViewModel() {

    private val _screenState =
        MutableStateFlow<AuthScreenState>(AuthScreenState.Start())
    val screenState: StateFlow<AuthScreenState> = _screenState.asStateFlow()

    fun onEvent(event: AuthScreenEvents) {
        when (event) {
            is AuthScreenEvents.CheckAuthCode -> {
                val phone = (_screenState.value as AuthScreenState.Code).phone
                checkAuthCode(event.code, phone)
            }

            is AuthScreenEvents.ReturnToSendPage -> {
                _screenState.value = AuthScreenState.Start()
            }

            is AuthScreenEvents.SendAuthCode -> {
                sendAuthCode(event.phone)
            }
        }
    }

    private fun sendAuthCode(phone: String) {
        viewModelScope.launch {
            _screenState.value = AuthScreenState.Loading
            val formatedPhone = if (phone.startsWith("+")) phone else "+$phone"

            sendAuthUseCase(SendAuthData(formatedPhone)).collectLatest {
                if (it is Response.Success) {
                    if (it.result.isSuccess)
                        _screenState.value = AuthScreenState.Code(formatedPhone)
                    else
                        _screenState.value =
                            AuthScreenState.Start("Ошибка при отправке СМС. Попробуйте позже.")
                } else {
                    _screenState.value = AuthScreenState.Start((it as Response.Error).message.msg)
                }
            }
        }
    }

    private fun checkAuthCode(code: String, phone: String) {
        viewModelScope.launch {
            _screenState.value = AuthScreenState.Loading
            checkAuthUseCase.invoke(CheckAuthData(phone, code)).collectLatest {
                if (it is Response.Success) {
                    if (it.result.isUserExists && it.result.isUserExists)
                        _screenState.value = AuthScreenState.Logged
                    else
                        _screenState.value = AuthScreenState.Code(phone, "Пользователь не найден.")
                } else {
                    _screenState.value =
                        AuthScreenState.Code(phone, (it as Response.Error).message.msg)
                }
            }
        }
    }

}